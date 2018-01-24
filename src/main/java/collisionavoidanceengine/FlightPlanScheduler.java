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
import config.Config;

import java.util.*;

import static collisionavoidanceengine.constants.Constant.*;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanScheduler {
    public int currentTime=0;
    public PriorityQueue<Request> myRequestQ;
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan;
    // Solution is a map of routing result
    public Map<String, RoutingResult> solution;
    public int UAVCounter =0;

    public ArrayList<String> solutionSingleTripTemp ;

    public FlightPlanScheduler(Config config){
        // Initialization
        try {
            PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator(config.pathToMap);
            myAirMap=pl.createAirspaceStructure();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
        // Notice that request must be initialized after AirMap is created
        // This is because RequestQueue will need the topology of AirMap
        try{
            RequestCreatorSelector rcs = new RequestCreatorSelector(config);
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
    public void printOpenList(PriorityQueue<WorkingTableEntry> open){
        System.out.printf("The current open list is:");
        PriorityQueue<WorkingTableEntry> openCopy = new PriorityQueue<>(open);
        for (WorkingTableEntry node : open){
            WorkingTableEntry curNode = openCopy.poll();
            System.out.printf(curNode.wp.getNodeID()+"("+curNode.fCost+","+curNode.gCost+") ->");
        }
        System.out.printf("\n");
    }

    public void updateSchedule(double startTime, String flightID){
        double reachTime = startTime;
        for (int i = 0; i<solutionSingleTripTemp.size()-1;i++){
            String currentWP  = solutionSingleTripTemp.get(i);
            String nextWP     = solutionSingleTripTemp.get((i+1));
            String routeSegID = "RS_"+currentWP.substring(3)+"-"+nextWP.substring(3);

            // Update the edge availability
            System.out.printf("Updating "+routeSegID+" \n");
            currentFlightPlan.edgeAvailability.get(routeSegID).addRecord(flightID, reachTime);

            // Update the node availability
            reachTime += myAirMap.getEdges().getRouteSegmentByID(routeSegID).getLength()/UAV_SPEED;
            if(i==0||i==solutionSingleTripTemp.size()-1){
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
        double passingTime = myAirMap.getEdges().getRouteSegmentByID(edgeName).getLength();

        passingTime=passingTime/UAV_SPEED;
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

                    // Backtrack to get all the route segments on the flight path
                    String anchor = succ.getNodeID();
                    solutionSingleTripTemp.add(anchor);
                    System.out.printf("Shortest path for "+req.getRequestID()+" is : (goal)"+anchor);

                    while (!PARENT.get(anchor).equals(origin.getNodeID())){
                            anchor=PARENT.get(anchor);
                            // get previous WP ID, and push to solutionTemp queue
                            solutionSingleTripTemp.add(anchor);
                            System.out.printf("  <-"+anchor);
                    }

                    // Only origin on the path has a null pointer
                    solutionSingleTripTemp.add(origin.getNodeID());
                    System.out.printf("  <-"+origin.getNodeID()+"\n");

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
                for (WorkingTableEntry td : OPEN){
                    if(td.wp.getNodeID().equals(succ.getNodeID())){
                        // No need to have two same node in OPEN
                        hasUpdatedOpen = true;
                        if(td.fCost > succFCost){
                            PARENT.put(td.wp.getNodeID(),currentNode.getNodeID());
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
                OPEN.add(new WorkingTableEntry(succ,succFCost,succGCost));
            }
            CLOSED.add(currentWorkingTableEntry);
            // Push current node to CLOSED list
        }
        // When no solution is found, return error message
        return -1;
    }

    public void doSegmentation(Request request){
        // Note : Be careful with current time
        int numNodesInSingleTrip = solutionSingleTripTemp.size();
        // try with only one en-route
        Request trip1 = new Request("REQ-Temp1",request.getOriginID(),solutionSingleTripTemp.get(numNodesInSingleTrip/2),request.getStartTime());
        System.out.printf(SPACER);
        double costTrip1 = doModifiedAStar(trip1,currentTime);

        double trip2StartTime = request.getStartTime()+costTrip1+WAITING_PENALTY_AT_LANDING_NODE;
        System.out.printf(SPACER);
        Request trip2 = new Request("REQ-Temp2",solutionSingleTripTemp.get((numNodesInSingleTrip+1)/2),request.getDestinationID(), (int) trip2StartTime);
        double costTrip2 = doModifiedAStar(trip2,(int)trip2StartTime);
         if (costTrip1<=15 && costTrip2 <=15){
             System.out.printf("  Can use 2 legs to deliver!\n");
             return;
         }
         else{
             System.out.printf("  ***Cannot do just two trips!***\n");
         }

    }

    public void ScheduleFlight() {
        currentTime = 0;
        while (!myRequestQ.isEmpty()) {

            // Fast forward to next request time
            while(myRequestQ.peek().getStartTime()>currentTime)
                currentTime++;

            Request currentRequest = myRequestQ.poll();
            System.out.printf("\nNow routing request "+currentRequest.getRequestID()+"\n");
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
                updateSchedule(currentRequest.getStartTime(), flightID);

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

            // Clean up the temporary variable
            solutionSingleTripTemp = new ArrayList<>();

        }
        System.out.printf("All Requests Finished Scheduling. ");
    }

    /**
     *       Write the request priority queue into csv file in form of
     *       < requestID, Time, source, destination>
     */
//    @Override
//    public void writeToCsv(Config config， ) {
//        FileWriter fileWriter = null;
//
//        String csvHeader = "requestID, UAVID, startTime, route";
//
//        try {
//            fileWriter = new FileWriter(config.outputPathRoot+"/RoutingResult.csv");
//
//            fileWriter.append(csvHeader + "\n");
//
//            for (String wpID : solutionSingleTripTemp) {
//                fileWriter.append(wpID + ",");
//            }
//        } catch (Exception e) {
//            System.out.println("Error in CsvFileWriter !!!");
//            e.printStackTrace();
//        } finally {
//            try {
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                System.out.println("Error while flushing/closing fileWriter !!!");
//                e.printStackTrace();
//            }
//
//
//        }
//    }
//}



}
