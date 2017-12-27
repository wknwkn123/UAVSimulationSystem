package collisionavoidanceengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.assets.Thread;
import collisionavoidanceengine.assets.ThreadComparator;
import collisionavoidanceengine.flightplan.FlightSchedule;

import collisionavoidanceengine.request.Request;
import collisionavoidanceengine.request.RequestCreatorSelector;


import javax.xml.soap.Node;
import java.io.IOException;
import java.util.*;

import static collisionavoidanceengine.constants.Constant.BATTERY_LIFE;
import static collisionavoidanceengine.constants.Constant.INITIAL_FLIGHT_CAPACITY;
import static collisionavoidanceengine.constants.Constant.WAITING_PENALTY_AT_NON_LANDING_NODE;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanSchedueler {
    public int currentTime=0;
    public PriorityQueue<Request> myRequestQ;
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan = new FlightSchedule(myAirMap);
    // Solution is presented in reversed order
    List<Waypoint> solution = new ArrayList<>();

    FlightPlanSchedueler(String airMapType, String requestQueueTyoe){
        // Initialization
        try {
            AirspaceEngine myAirEngine = AirspaceEngine.getInstance();
            // TODO:to be substituted with airMapType
            myAirEngine.createAirspace("PLANARGRAPH");
            myAirMap=myAirEngine.airMap;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
        // Notice that request must be initialized after AirMap is created
        // This is because RequestQueue will require the topology of AirMap
        try{
            // to be substituted with requestQueueTyoe
            RequestCreatorSelector rcs = new RequestCreatorSelector();
            rcs.setRequestCreator("RANDOM");
            myRequestQ = rcs.getRequestCreator().generateRequest(INITIAL_FLIGHT_CAPACITY,myAirMap);
        }
        catch (Exception e){
            e.printStackTrace();;
            System.out.printf("ERROR: CANNOT CREATE REQUEST QUEUE!");
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
        // The following thread refers to Thread class under collisionavoidanceengine.asset
        Comparator <Thread> threadComparator = new ThreadComparator();

        // Initialize the "OPEN" list, which stores the frontier nodes
        PriorityQueue<Thread> OPEN = new PriorityQueue<Thread>(myAirMap.getNodes().getSize(),threadComparator);
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
        OPEN.add(new Thread(origin,
                calcEuclideanDistance(origin,goal)+currentFlightPlan.getWaitingPenaltyAtNode(origin.getNodeID(),currentTime),
                calcEuclideanDistance(origin,goal)));
        NodeInOpen.add(origin.getNodeID());

        while(!OPEN.isEmpty()){
            //Pop the Thread(node) with the least cost
            Thread currentThread = OPEN.poll();
            double costSoFar = currentThread.gCost;
            Waypoint currentNode = currentThread.wp;

            // Generate all successors of current node using adjacency node list
            for (Waypoint succ : currentNode.getAdjacientWaypoints()){
                PARENT.put(succ,currentNode);
                // If the successor is in goal state
                if (succ.getNodeID().equals(goal.getNodeID())){
                    // Update parent info
                    PARENT.put(succ,currentNode);
                    // Backtrack to get all the route segments on the flight path
                    Waypoint anchor = goal;
                    List<Waypoint> solutionTemp = new ArrayList<>();
                    solutionTemp.add(anchor);
                    while (PARENT.get(anchor)!=origin){
                        anchor=PARENT.get(anchor);
                        solutionTemp.add(anchor);
                    }
                    solutionTemp.add(origin);

                    for (int i=solutionTemp.size()-1;i>=0;i--){
                        solution.add(solutionTemp.get(i));
                    }

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
                for (Thread td : OPEN){
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

                OPEN.add(new Thread(succ,succFCost,succGCost));
            }
            // Push current node to CLOSED list
        }
        // When no solution is found, return error message
        return -1;
    }


    public List<Waypoint> ScheduleFlight(){
        currentTime=0;
        while(!myRequestQ.isEmpty()){
            Request currentRequest = myRequestQ.poll();
            double requestedTime=doModifiedAStar(currentRequest,currentTime);
            if (requestedTime<=BATTERY_LIFE/2 &&requestedTime>0){
                // Add to flight path
                // Update all nodes and edges on the flight path
                return this.solution;
            }

                //todo: implement DFS
                // Do a DFS search on all possible routing combinations

        }
        return null;
    }


}
