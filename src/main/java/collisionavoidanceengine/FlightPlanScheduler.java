package collisionavoidanceengine;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.airspacestructure.PlanarAirspaceStructureCreator;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.runtime.RoutingResult;
import collisionavoidanceengine.runtime.WorkingTableEntry;
import collisionavoidanceengine.runtime.WorkingTableEntryComparator;
import collisionavoidanceengine.flightplan.FlightSchedule;

import collisionavoidanceengine.request.Request;
import collisionavoidanceengine.request.RequestCreatorSelector;
import mapbuilder.triangulation.Node;


import java.util.*;

import static collisionavoidanceengine.constants.Constant.BATTERY_LIFE;
import static collisionavoidanceengine.constants.Constant.INITIAL_FLIGHT_CAPACITY;
import static collisionavoidanceengine.constants.Constant.MAX_DELAY;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanScheduler {
    public final int MAX_CONNECTIONS = 4;
    public int currentTime=0;
    public PriorityQueue<Request> myRequestQ;
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan;
    // Solution is a map of routing result
    public Map<String, RoutingResult> solution;
    public int UAVCounter =0;

    public ArrayList<String> solutionSingleTripTemp ;

    public FlightPlanScheduler(String airMapType, String requestQueueTyoe){
        // Initialization

        try {
            PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
            myAirMap=pl.createAirspaceStructure();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
        // Notice that request must be initialized after AirMap is created
        // This is because RequestQueue will need the topology of AirMap
        try{
            RequestCreatorSelector rcs = new RequestCreatorSelector("RANDOM");
            myRequestQ = rcs.getRequestCreator().generateRequest(INITIAL_FLIGHT_CAPACITY,myAirMap);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.printf("ERROR: CANNOT CREATE REQUEST QUEUE!");
        }

        this.currentFlightPlan = new FlightSchedule(myAirMap);
        this.solutionSingleTripTemp = new ArrayList<>();
        this.solution = new HashMap<>();

    }

    public void updateSchedule(double startTime, String flightID, ArrayList<String> flightPath){
        double reachTime = startTime;
        for (int i = 0; i<flightPath.size();i++){
            String currentWP  = flightPath.get(i);
            String nextWP     = flightPath.get((i+i));
            String routeSegID = "RS_"+currentWP.substring(3)+"-"+nextWP.substring(3);

            // Update the edge availability
            currentFlightPlan.edgeAvailability.get(routeSegID).addRecord(flightID, reachTime);


            // Update the node availability
            reachTime += myAirMap.getEdges().getRouteSegmentByID(routeSegID).getWeight();
            if(i==0||i==flightPath.size()-1){
                if(i==0)
                    // if this is the origin, set isTakingOff to true
                    currentFlightPlan.nodeAvailability.get(currentWP).addRecord(reachTime,flightID,false,true);
                else
                    // if this is the destination, set isLanding to true
                    currentFlightPlan.nodeAvailability.get(currentWP).addRecord(reachTime,flightID,true,false);
            }
            else
                // those are non-landing nodes in between
                currentFlightPlan.nodeAvailability.get(currentWP).addRecord(reachTime,flightID,false,false);
        }
    }

    // Heuristic Function
    public double calcEuclideanDistance(Waypoint origin, Waypoint target){
        return Math.sqrt((origin.getX()-target.getX())*(origin.getX()-target.getX())+(origin.getY()-target.getY())*(origin.getY()-target.getY()));
    }

    // Uniform-cost search
    public double calcActualCost(double costSoFar, int currentTime , Waypoint currentNode, Waypoint successor){
        String edgeName = "RS_"+currentNode.getNodeID().substring(3)+"-"+successor.getNodeID().substring(3);
        double passingTime = myAirMap.getEdges().getRouteSegmentByID(edgeName).getWeight();
        return costSoFar
                + currentFlightPlan.getWaitingPenaltyAtEdge(edgeName,currentTime)
                + currentFlightPlan.getWaitingPenaltyAtNode(successor.getNodeID(),currentTime+passingTime);
    }

    public double doModifiedAStar(Request req, int currentTime){
        // The following thread refers to WorkingTableEntry class under collisionavoidanceengine.asset
        Comparator <WorkingTableEntry> entryComparator = new WorkingTableEntryComparator();

        // Initialize the "OPEN" list, which stores the frontier nodes
        PriorityQueue<WorkingTableEntry> OPEN = new PriorityQueue<WorkingTableEntry>(myAirMap.getNodes().getSize(),entryComparator);

//        // To facilitate search, we use a set.
//        // NOTICE : every addition/deletion to OPEN must have a counterpart to NodeInOpen set
//        Set<String> NodeInOpen = new HashSet<>();

        // Initialize the PARENT list, which stores the parent information in form of <current WP, previous WP>
        Map<Waypoint, Waypoint> PARENT = new HashMap<>();
        for (Waypoint wp : myAirMap.getNodes().getWaypointList())
            PARENT.put(wp,null);

        Waypoint origin = myAirMap.getNodes().getWaypointByID(req.getOriginID());
        Waypoint goal = myAirMap.getNodes().getWaypointByID(req.getDestinationID());


        // Put the origin to OPEN list
        OPEN.add(new WorkingTableEntry(origin,
                calcEuclideanDistance(origin,goal)
                        +currentFlightPlan.getWaitingPenaltyAtNode(origin.getNodeID(),currentTime),
                calcEuclideanDistance(origin,goal)));
//        NodeInOpen.add(origin.getNodeID());

        while(!OPEN.isEmpty()){
            //Pop the WorkingTableEntry(node) with the least cost
            WorkingTableEntry currentWorkingTableEntry = OPEN.poll();
            double costSoFar = currentWorkingTableEntry.gCost;
            Waypoint currentNode = currentWorkingTableEntry.wp;

            // Generate all successors of current node using adjacency node list
            for (Waypoint succ : currentNode.getAdjacientWaypoints()){

                // If the successor is in goal state
                if (succ.getNodeID().equals(goal.getNodeID())){
                    // Update their parent node information
                    PARENT.put(succ,currentNode);

                    // Backtrack to get all the route segments on the flight path
                    Waypoint anchor = goal;
                    while (PARENT.get(anchor)!=origin){
                        //System.out.printf("        <-"+anchor.getNodeID());
                        // get parent, and push to solutionTemp queue
                        solutionSingleTripTemp.add(anchor.getNodeID());
                        anchor=PARENT.get(anchor);
                    }
                    solutionSingleTripTemp.add(origin.getNodeID());

                    // Reverse the solutionTemp queue so that the first element is origin node
                    Collections.reverse(solutionSingleTripTemp);

                    return costSoFar;
                }

                double succHCost = calcEuclideanDistance(succ,goal);
                double succGCost = calcActualCost(costSoFar,currentTime, currentNode,succ);
                double succFCost = succGCost+succHCost;

                /*
                    if a node with the same position as successor is in the OPEN list \
	                 which has a lower f than successor, skip this successor
                 */
                boolean hasUpdatedOpen = false;

                // TODO : can optimize the code here by reducing search time
                for (WorkingTableEntry td : OPEN){
                    if(td.wp.getNodeID().equals(succ.getNodeID())&&td.fCost < succFCost){
                        PARENT.put(td.wp,currentNode);
                        td.fCost = succFCost;
                        td.gCost = succGCost;
                        hasUpdatedOpen = true;
                        break;
                    }
                }

                if (hasUpdatedOpen)
                    continue;

                /*
                    if a node with the same position as successor is in the CLOSED list \
	                which has a lower f than successor, skip this successor
                 */
//                boolean hasUpdatedClose = false;
//                for (Waypoint wp : CLOSED){
//
//                }

                // Update their parent node information
                PARENT.put(succ,currentNode);
                OPEN.add(new WorkingTableEntry(succ,succFCost,succGCost));
//                NodeInOpen.add(succ.getNodeID());
            }
            // Push current node to CLOSED list
        }
        // When no solution is found, return error message
        return -1;
    }

    public void doSegmentation(Request request){

    }


    public void ScheduleFlight() {
        currentTime = 0;
        while (!myRequestQ.isEmpty()) {

            // Fast forward to next request time
            while(myRequestQ.peek().getStartTime()>currentTime)
                currentTime++;

            Request currentRequest = myRequestQ.poll();
            System.out.printf("Now routing request "+currentRequest.getRequestID()+"n");
            double requestedTime = doModifiedAStar(currentRequest, currentTime);
            if (requestedTime <= BATTERY_LIFE / 2 && requestedTime > 0) {
                //  Make a new RoutingResult record
                String flightID = "FL_" + currentRequest.getRequestID().substring(3);

                // UAVID value is incremented by 1
                UAVCounter++;
                currentRequest.setUAVID("UV_" + UAVCounter);

                // Add this routing result to solution
                RoutingResult curResult = new RoutingResult(flightID, currentRequest.getStartTime(), requestedTime, solutionSingleTripTemp);
                solution.put(curResult.getFlightID(), curResult);

                // Output the result to console
                System.out.printf("Found a single-trip solution for request " + currentRequest.getRequestID() + "that goes through ");
                for (String nodeID : solutionSingleTripTemp)
                    System.out.printf(nodeID + " -> ");
                System.out.printf("\n");

                // update all nodes and edges on the flight path
                updateSchedule(currentRequest.getStartTime(), flightID, solutionSingleTripTemp);

                // Clean up the temporary variable
                solutionSingleTripTemp = new ArrayList<>();

            } else
            // Find viable connections
            {   // Segment the flight path into multiple shorter trips with similar number of stops in between
                try {
                    doSegmentation(currentRequest);
                } catch (Exception e) {
                    System.out.printf("");
                    // delay to re-route later
                    System.out.printf("Need to re-route!");
                    Random randomNum = new Random();
                    currentRequest.setStartTime(currentTime + 1 + randomNum.nextInt(MAX_DELAY - 1));

                }
            }
        }
    }


}
