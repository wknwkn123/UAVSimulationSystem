package airspaceengine.waypoint;

import uav.UAV;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Waypoint {
    //instance variables
    private final String NodeID;    // In form of WP_ID
    private final boolean isTransferable;
    private final double x;
    private final double y;
    private final double z;
    private PriorityQueue<UAV> uavPriorityQueue;
    private List<Waypoint> AdjacentWaypoint = new ArrayList<>();  // List of successor Waypoint ID <>

    //constructor
    public Waypoint(String nodeID, boolean isTransferable, double x_input, double y_input, double z_input) {
        NodeID = nodeID;
        this.isTransferable = isTransferable;
        x = x_input;
        y = y_input;
        z = z_input;
        // PriorityQueue<UAV> uavPQ
        //this.uavPriorityQueue = new PriorityQueue<>();
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