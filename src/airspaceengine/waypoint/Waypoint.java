package airspaceengine.waypoint;

import java.util.List;

public class Waypoint {

    //instance variables
    private final String NodeID;    // In form of WP_ID
    private final boolean isTransferable;
    private final double x;
    private final double y;
    private final double z;
    private List<Waypoint> AdjacentWaypoint;

    //constructor
    public Waypoint(String nodeID, boolean isTransferable, double x_input, double y_input, double z_input) {
        NodeID = nodeID;
        this.isTransferable = isTransferable;
        x = x_input;
        y = y_input;
        z = z_input;
    }

    //getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }


    public List<Waypoint> getAdjacientWaypoints() {
        return AdjacentWaypoint;
    }

    public boolean isTransferable() {
        return isTransferable;
    }

    public String getNodeID() {
        return NodeID;
    }

    public void addAdjacentWaypoint(Waypoint wp) {
        AdjacentWaypoint.add(wp);
    }
}
