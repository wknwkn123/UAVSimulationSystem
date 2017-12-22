package airspaceengine.routesegment;

import airspaceengine.waypoint.Waypoint;

/**
 *
 * RouteSegment is the edges of mapbuilder.graph, its ID begins with RS.
 *
 * Assuming UAV capacity is equal to 1.
 */

public class RouteSegment {

    //instance variables
    private final String EdgeID;        // in form of RS_id
    private final Waypoint sourceWaypoint;
    private final Waypoint destinationWaypoint;
    private double targetSpeed;
    private double actualSpeed;
    private int UAVCapacity;
    private final double weight;

    //constructor
    public RouteSegment(String edgeID, Waypoint origin, Waypoint end, double weight) {
        EdgeID = edgeID;
        this.sourceWaypoint = origin;
        this.destinationWaypoint = end;
        this.weight = weight;
    }

    //getter methods
    public Waypoint getSource() {
        return this.sourceWaypoint;
    }

    public Waypoint getDestination() {
        return this.destinationWaypoint;
    }

    public double getTargetSpeed() {
        return targetSpeed;
    }

    public double getActualSpeed() {
        return actualSpeed;
    }

    public double getWeight() {
        return weight;
    }

    public String getEdgeID() {
        return EdgeID;
    }
}
