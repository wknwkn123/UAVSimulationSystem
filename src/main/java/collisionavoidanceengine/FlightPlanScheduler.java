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
import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import mapbuilder.triangulation.Node;


import javax.swing.text.html.HTMLDocument;
import java.util.*;

import static collisionavoidanceengine.constants.Constant.*;

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

    // Print the Open PriorityQueue (the unexplored nodes)
    public void printOpen(PriorityQueue<WorkingTableEntry> open){
        System.out.printf("The current open list is:");
        PriorityQueue<WorkingTableEntry> openCopy = new PriorityQueue<>(open);
        for (WorkingTableEntry node : open){
            WorkingTableEntry curNode = openCopy.poll();
            System.out.printf(curNode.wp.getNodeID()+"("+curNode.fCost+","+curNode.gCost+") ->");
        }
        System.out.printf("\n");
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
        double distanceInMeter =  Math.sqrt((origin.getX()-target.getX())*(origin.getX()-target.getX())+(origin.getY()-target.getY())*(origin.getY()-target.getY()));
        return distanceInMeter/UAV_SPEED;
    }

    // Uniform-cost search
    public double calcActualCost(double costSoFar, int currentTime , Waypoint currentNode, Waypoint successor){
        String edgeName = "RS_"+currentNode.getNodeID().substring(3)+"-"+successor.getNodeID().substring(3);
        double passingTime = myAirMap.getEdges().getRouteSegmentByID(edgeName).getWeight()/UAV_SPEED;
        double cost = costSoFar
                + passingTime
                + currentFlightPlan.getWaitingPenaltyAtEdge(edgeName,currentTime)
                + currentFlightPlan.getWaitingPenaltyAtNode(successor.getNodeID(),currentTime+passingTime);
        return cost;
    }

    public double doModifiedAStar(Request req, int currentTime){
        // The following thread refers to WorkingTableEntry class under collisionavoidanceengine.asset
        Comparator <WorkingTableEntry> entryComparator = new WorkingTableEntryComparator();

        // Initialize the "OPEN" list, which stores the frontier nodes
        PriorityQueue<WorkingTableEntry> OPEN = new PriorityQueue<WorkingTableEntry>(myAirMap.getNodes().getSize(),entryComparator);

        // This stored the nodes whose successors are fully explored
        ArrayList<WorkingTableEntry> CLOSED = new ArrayList<>();

        // Initialize the PARENT list, which stores the parent information in form of <current WP, previous WP>
        Map<String, String> PARENT = new HashMap<>();
        for (Waypoint wp : myAirMap.getNodes().getWaypointList())
            PARENT.put(wp.getNodeID(),null);

        Waypoint origin = myAirMap.getNodes().getWaypointByID(req.getOriginID());
        Waypoint goal = myAirMap.getNodes().getWaypointByID(req.getDestinationID());


        // Put the origin to OPEN list
        // Note that, we need to add the penalty for using origin node for taking off
        OPEN.add(new WorkingTableEntry(origin,
                calcEuclideanDistance(origin,goal)+currentFlightPlan.getWaitingPenaltyAtNode(origin.getNodeID(),currentTime),
                currentFlightPlan.getWaitingPenaltyAtNode(origin.getNodeID(),currentTime)));
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
                    PARENT.put(succ.getNodeID(),currentNode.getNodeID());
                    System.out.printf("--1---Parent of "+succ.getNodeID()+"is set to"+currentNode.getNodeID()+"\n");

                    // Backtrack to get all the route segments on the flight path
                    String anchor = succ.getNodeID();
                    solutionSingleTripTemp.add(anchor);
                    System.out.printf("Shortest path for "+req.getRequestID()+" is : (goal)"+anchor);

                    int count = 0;

                    while (!PARENT.get(anchor).equals(origin.getNodeID())&&count<=10){
                            anchor=PARENT.get(anchor);
                            // get previous WP ID, and push to solutionTemp queue
                            solutionSingleTripTemp.add(anchor);
                            System.out.printf("  <-"+anchor);

                            count++;
                    }

                    // Only origin on the path has a null pointer

                    solutionSingleTripTemp.add(origin.getNodeID());
                    System.out.printf("  <-"+origin.getNodeID()+"\n");

                    for (String key : PARENT.keySet()){
                        System.out.printf("Key : "+key + "    Parent : "+PARENT.get(key) +"\n");
                    }


                    // Reverse the solutionTemp queue so that the first element is origin node
                    Collections.reverse(solutionSingleTripTemp);

                    return costSoFar;
                }

                double succHCost = calcEuclideanDistance(succ,goal);
                double succGCost = calcActualCost(costSoFar,currentTime, currentNode,succ);
                double succFCost = succGCost+succHCost;

                /*
                    if a node with the same position as successor is in the OPEN list \
	                 which has a lower f than successor, update that working table entry and skip this successor
                 */
                boolean hasUpdatedOpen = false;

                // TODO : can optimize the code here by reducing search time
                // TODO : add UAV speed to be an attribute of UAV
                // TODO : make number of UAV to be a attribute of Node
                for (WorkingTableEntry td : OPEN){
                    if(td.wp.getNodeID().equals(succ.getNodeID())){
                        // No need to have two same node in OPEN
                        hasUpdatedOpen = true;
                        System.out.printf("Found "+succ.getNodeID()+" in OPEN!");
                        if(td.fCost > succFCost){
                            PARENT.put(td.wp.getNodeID(),currentNode.getNodeID());
                            System.out.printf("--2.Open---Parent of "+td.wp.getNodeID()+"is set to"+currentNode.getNodeID()+"\n");
                            td.fCost = succFCost;
                            td.gCost = succGCost;
                            break;
                        }
                    }
                }
                if (hasUpdatedOpen)
                    continue;

                /*
                    if a node with the same position as successor is in the CLOSED list \
	                 which has a lower f than successor, update that working table entry and skip this successor
                 */
                boolean hasUpdatedClosed = false;
                for (WorkingTableEntry wte: CLOSED){
                    if (wte.wp.getNodeID().equals(succ.getNodeID())){
                        hasUpdatedClosed=true;
                        if (wte.fCost > succFCost){
                            PARENT.put(wte.wp.getNodeID(),currentNode.getNodeID());
                            System.out.printf("--2.Closed---Parent of "+wte.wp.getNodeID()+"is set to"+currentNode.getNodeID()+"\n");
                            wte.fCost = succFCost;
                            wte.gCost = succGCost;
                            break;
                        }

                    }
                }
                if (hasUpdatedClosed)
                    continue;

                // Update their parent node information
                PARENT.put(succ.getNodeID(),currentNode.getNodeID());
                System.out.printf("--3---Parent of "+succ.getNodeID()+"is set to"+currentNode.getNodeID()+"\n");
                OPEN.add(new WorkingTableEntry(succ,succFCost,succGCost));
                printOpen(OPEN);
//                NodeInOpen.add(succ.getNodeID());
            }
            CLOSED.add(currentWorkingTableEntry);
            // Push current node to CLOSED list
        }
        // When no solution is found, return error message
        return -1;
    }

    public void doSegmentation(Request request){
        // todo : be careful with current time
        int numNodesInSingleTrip = solutionSingleTripTemp.size();
        // try with only one en-route
        Request trip1 = new Request("REQ-Temp1",request.getOriginID(),solutionSingleTripTemp.get(numNodesInSingleTrip/2),request.getStartTime());
        double costTrip1 = doModifiedAStar(trip1,currentTime);

        double trip2StartTime = request.getStartTime()+costTrip1+WAITING_PENALTY_AT_LANDING_NODE;
        Request trip2 = new Request("REQ-Temp2",solutionSingleTripTemp.get((numNodesInSingleTrip+1)/2),request.getDestinationID(), (int) trip2StartTime);
        double costTrip2 = doModifiedAStar(trip2,(int)trip2StartTime);
         if (costTrip1>15 || costTrip2>15){
             System.out.printf("Cannot do just two trips!");
         }

    }

    public void ScheduleFlight() {
        currentTime = 0;
        while (!myRequestQ.isEmpty()) {

            // Fast forward to next request time
            while(myRequestQ.peek().getStartTime()>currentTime)
                currentTime++;

            Request currentRequest = myRequestQ.poll();
            System.out.printf("Now routing request "+currentRequest.getRequestID()+"\n");
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
        System.out.printf("All Requests Finished Scheduling. ");
    }


}
