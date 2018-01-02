package collisionavoidanceengine.flightplan;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.routesegment.RSMap;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.WPMap;
import airspaceengine.waypoint.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class FlightSchedule {
    private AirspaceStructure airmap;
    public Map<String, EdgeRecord> edgeAvailability;
    public Map<String, NodeRecord> nodeAvailability;
    private Map<String, Flight> flightPlan;   // <flightID, flight>

    // To ensure thread-safety. Not yet used.
    private boolean mutex;

    public FlightSchedule(AirspaceStructure airmap) {

        WPMap myWPL = airmap.getNodes();
        RSMap myRSL = airmap.getEdges();

        // Initialize node availability
        for (Map.Entry<String, Waypoint> entry : myWPL.getWaypointMap().entrySet()) {
            NodeRecord nr = new NodeRecord(entry.getKey(), entry.getValue().isTransferable());
            nodeAvailability.put(nr.getNodeID(),nr);
        }

        // Initialize edge availability
        for (Map.Entry<String, RouteSegment> entry : myRSL.getRouteSegMap().entrySet()) {
            EdgeRecord rs = new EdgeRecord(entry.getKey(),entry.getValue().getWeight());
            edgeAvailability.put(rs.getEdgeID(),rs);
        }
    }

    public EdgeRecord getEdgeRecByID(String edgeID){
        if (edgeAvailability.containsKey(edgeID)){
            return edgeAvailability.get(edgeID);
        }
        else
            return null;
    }

    public NodeRecord getNodeRecByID(String nodeID){
        if (nodeAvailability.containsKey(nodeID))
            return nodeAvailability.get(nodeID);
        else
            return null;
    }


    // Get the waiting period for waiting at a specific node
    public double getWaitingPenaltyAtNode(String nodeID, double time){
        return this.getNodeRecByID(nodeID).getWaitingPenalty(time);
    }

    // Get the waiting period for using a specific edge
    public  double getWaitingPenaltyAtEdge(String edgeID, int time){
        return  this.getEdgeRecByID(edgeID).getWaitingPenalty(time);
    }


    public Map<String, Flight> getFlightPlan() {
        return flightPlan;
    }

    public void setFlightPlan(Map<String, Flight> flightPlan) {
        this.flightPlan = flightPlan;
    }

    // Search individual flight
    public Flight getFlightPlanByID(String flightID) {
        return this.flightPlan.get(flightID);
    }

    public void setFlightPlanByID(String flightID, ArrayList<String> path) {
        this.flightPlan.get(flightID).setFlightPath(path);
    }
}
