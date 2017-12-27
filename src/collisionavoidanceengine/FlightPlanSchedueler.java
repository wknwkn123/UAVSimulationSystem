package collisionavoidanceengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.assets.Thread;
import collisionavoidanceengine.assets.ThreadComparator;
import collisionavoidanceengine.flightplan.FlightSchedule;

import collisionavoidanceengine.request.Request;
import collisionavoidanceengine.request.RequestCreatorSelector;


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
        return costSoFar
                + currentFlightPlan.getWaitingPenaltyAtEdge(edgeName,currentTime)
                + currentFlightPlan.getWaitingPenaltyAtNode(successor.getNodeID(),currentTime);
    }

    // Weight = heuristic cost + Dijkstra cost
    public double totalCost(double costSoFar, int currentTime ,Waypoint currentNode, Waypoint successorNode, Waypoint goal){
        double h = calcEuclideanDistance(currentNode,goal);
        double g = calcActuralCost(costSoFar,currentTime,currentNode,successorNode);
        return h+g;
    }

    // TODO : Backtrack to print the routing history
    public int backTrack(){

    }



    public int doModifiedAStar(Request req, int currentTime){
        // The following thread refers to Thread class under collisionavoidanceengine.asset
        Comparator <Thread> threadComparator = new ThreadComparator();

        // Initialize the "OPEN" and "CLOSED" list
        PriorityQueue<Thread> OPEN = new PriorityQueue<Thread>(myAirMap.getNodes().getSize(),threadComparator);
        List<Waypoint> CLOSED = new LinkedList<>();

        Waypoint origin = myAirMap.getNodes().getWaypointByID(req.getOriginID());
        Waypoint goal = myAirMap.getNodes().getWaypointByID(req.getDestinationID());

        // Put the origin to OPEN list
        OPEN.add(new Thread(origin,calcEuclideanDistance(origin,goal)+0,calcEuclideanDistance(origin,goal)));

        while(!OPEN.isEmpty()){
            //Pop the Thread(node) with the least cost
            Thread currentThread = OPEN.poll();
            double costSoFar = currentThread.gCost;
            Waypoint currentNode = currentThread.wp;

            // Generate all successors of current node using adjaciency node list
            for (Waypoint succ : currentNode.getAdjacientWaypoints()){
                // If goal state reached
                if (succ.getNodeID().equals(goal.getNodeID()))
                    // TODO: figure out a way to manage the parent relationship between nodes.
                    return backTrack();
                double FCost=totalCost(costSoFar,currentTime,currentNode,succ,goal);
                for (Thread td :OPEN){
                    if (td.wp.getNodeID().equals(succ.getNodeID())&& td.fCost<FCost){
                        // Update its parent and all costs
                        td.fCost=FCost;
                    }

                for (Waypoint wp : CLOSED){

                }


                }

            }
            // Push current node to CLOSED list

        }
    }


    public void ScheduleFlight(){
        currentTime=0;
        while(!myRequestQ.isEmpty()){
            Request currentRequest = myRequestQ.poll();
            if (doModifiedAStar(currentRequest,currentTime)<=BATTERY_LIFE/2){
                // Add to flight path
                // Update all nodes and edges on the flight path
            }
            else
                // Do a DFS search on all possible routing combinations

        }
    }


}
