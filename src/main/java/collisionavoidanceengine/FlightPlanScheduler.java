package collisionavoidanceengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.assets.RoutingResult;
import collisionavoidanceengine.assets.WorkingTableEntry;
import collisionavoidanceengine.assets.WorkingTableEntryComparator;
import collisionavoidanceengine.flightplan.FlightSchedule;

import collisionavoidanceengine.request.Request;
import collisionavoidanceengine.request.RequestCreatorSelector;


import java.io.IOException;
import java.util.*;

import static collisionavoidanceengine.constants.Constant.BATTERY_LIFE;
import static collisionavoidanceengine.constants.Constant.INITIAL_FLIGHT_CAPACITY;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanScheduler {
    public int currentTime=0;
    public PriorityQueue<Request> myRequestQ;
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan = new FlightSchedule(myAirMap);
    // Solution is a map of routing result
    public Map<String, RoutingResult> solution = new HashMap<>();
    public int UAVCounter =0;

    ArrayList<String> solutionSingleTripTemp = new ArrayList<>();

    public FlightPlanScheduler(String airMapType, String requestQueueTyoe){
        System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        // Initialization
        try {
            AirspaceEngine myAirEngine = AirspaceEngine.getInstance();
            // TODO:to be substituted with airMapType
            System.out.printf("befroe AirMap created.");
            myAirEngine.createAirspace("PLANARGRAPH");
            System.out.printf("AirMap created.");
            myAirMap=myAirEngine.airMap;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
        // Notice that request must be initialized after AirMap is created
        // This is because RequestQueue will require the topology of AirMap
        try{
            // todo : to be substituted with requestQueueType
            RequestCreatorSelector rcs = new RequestCreatorSelector();
            rcs.setRequestCreator("RANDOM");
            myRequestQ = rcs.getRequestCreator().generateRequest(INITIAL_FLIGHT_CAPACITY,myAirMap);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.printf("ERROR: CANNOT CREATE REQUEST QUEUE!");
        }
    }

    public void updateSchedule(double startTime, String flightID, ArrayList<String> flightPath){
        double reachTime = startTime;
        for (int i = 0; i<flightPath.size();i++){
            String currentWP  = flightPath.get(i);
            String nextWP     = flightPath.get((i+i));
            String routeSegID = "RS_"+currentWP+"-"+nextWP;

            // Update the edge availability
            currentFlightPlan.edgeAvailability.get(routeSegID).addRecord(flightID,reachTime);


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
    public double calcActuralCost(double costSoFar, int currentTime ,Waypoint currentNode, Waypoint successor){
        String edgeName = "RS_"+currentNode.getNodeID().substring(3)+"-"+successor.getNodeID().substring(3);
        double passingTime = myAirMap.getEdges().getRouteSegmentByID(edgeName).getWeight();
        return costSoFar
                + currentFlightPlan.getWaitingPenaltyAtEdge(edgeName,currentTime)
                + currentFlightPlan.getWaitingPenaltyAtNode(successor.getNodeID(),currentTime+passingTime);
    }

    public double doModifiedAStar(Request req, int currentTime){
        // The following thread refers to WorkingTableEntry class under collisionavoidanceengine.asset
        Comparator <WorkingTableEntry> threadComparator = new WorkingTableEntryComparator();

        // Initialize the "OPEN" list, which stores the frontier nodes
        PriorityQueue<WorkingTableEntry> OPEN = new PriorityQueue<WorkingTableEntry>(myAirMap.getNodes().getSize(),threadComparator);
        // To facilitate search, we use a set. NOTICE : every addition/deletion to OPEN has to have a counterpart to NodeInOpen set
        Set<String> NodeInOpen = new HashSet<>();

        // Initialize the PARENT list, which stores the parent information
        Map<Waypoint, Waypoint> PARENT;
        PARENT = new HashMap<>();
        for (Waypoint wp : myAirMap.getNodes().getWaypointList())
            PARENT.put(wp,null);

        Waypoint origin = myAirMap.getNodes().getWaypointByID(req.getOriginID());
        Waypoint goal = myAirMap.getNodes().getWaypointByID(req.getDestinationID());

        // Put the origin to OPEN list
        OPEN.add(new WorkingTableEntry(origin,
                calcEuclideanDistance(origin,goal)+currentFlightPlan.getWaitingPenaltyAtNode(origin.getNodeID(),currentTime),
                calcEuclideanDistance(origin,goal)));
        NodeInOpen.add(origin.getNodeID());

        while(!OPEN.isEmpty()){
            //Pop the WorkingTableEntry(node) with the least cost
            WorkingTableEntry currentWorkingTableEntry = OPEN.poll();
            double costSoFar = currentWorkingTableEntry.gCost;
            Waypoint currentNode = currentWorkingTableEntry.wp;

            // Generate all successors of current node using adjacency node list
            for (Waypoint succ : currentNode.getAdjacientWaypoints()){
                PARENT.put(succ,currentNode);
                // If the successor is in goal state
                if (succ.getNodeID().equals(goal.getNodeID())){
                    // Update parent info
                    PARENT.put(succ,currentNode);
                    // Backtrack to get all the route segments on the flight path
                    Waypoint anchor = goal;

                    solutionSingleTripTemp.add(anchor.getNodeID());
                    while (PARENT.get(anchor)!=origin){
                        // get parent, and push to solutionTemp queue
                        anchor=PARENT.get(anchor);
                        solutionSingleTripTemp.add(anchor.getNodeID());
                    }
                    solutionSingleTripTemp.add(origin.getNodeID());

                    return costSoFar;
                }

                double succHCost = calcEuclideanDistance(succ,goal);
                double succGCost = calcActuralCost(costSoFar,currentTime, currentNode,succ);
                double succFCost = succGCost+succHCost;

                /*
                    if a node with the same position as successor is in the OPEN list \
	                 which has a lower f than successor, skip this successor
                 */
                boolean hasUpdatedOpen = false;
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

                OPEN.add(new WorkingTableEntry(succ,succFCost,succGCost));
            }
            // Push current node to CLOSED list
        }
        // When no solution is found, return error message
        return -1;
    }


    public void ScheduleFlight(){
        currentTime=0;
        while(!myRequestQ.isEmpty()){
            Request currentRequest = myRequestQ.poll();
            double requestedTime=doModifiedAStar(currentRequest,currentTime);
            if (requestedTime<=BATTERY_LIFE/2 &&requestedTime>0){
                //  Make a new RoutingResult record
                String flightID  = "FL_"+currentRequest.getRequestID().substring(3);

                // UAVID value is incremented by 1
                UAVCounter++;
                currentRequest.setUAVID("UV_"+UAVCounter);

                // Reverse the solutionTemp queue so that the first element is origi

                // Add this routing result to solution
                Collections.reverse(solutionSingleTripTemp);
                RoutingResult curResult  = new RoutingResult(flightID,currentRequest.getStartTime(),requestedTime,solutionSingleTripTemp);
                solution.put(curResult.getFlightID(),curResult);

                // update all nodes and edges on the flight path
                updateSchedule(currentRequest.getStartTime(),flightID, solutionSingleTripTemp);

                // Clean up the temporary variable
                solutionSingleTripTemp=null;

            }
            else
            {    //todo: implement DFS
                // Do a DFS search on all possible routing combinations
                System.out.printf("Need to re-route!");
            }
        }
    }


}
