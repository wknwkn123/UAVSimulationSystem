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
    private final Waypoint sourceWaypoint; // source node of edge
    private final Waypoint destinationWaypoint;  // destination node of edge
    private double targetSpeed;         // Estimated speed, used as 500m/s here to calculate the time to pass edge
    private double actualSpeed;         // Actual flying speed on that edge, may vary due to real-life conditions like weather, animal, or non-flying zone
    private int UAVCapacity;            // max weight that a UAV can carry
    private final double length;        // length of RouteSegment measured in meter

    //constructor
    public RouteSegment(String edgeID, Waypoint origin, Waypoint end, double length) {
        EdgeID = edgeID;
        this.sourceWaypoint = origin;
        this.destinationWaypoint = end;
        this.length = length;
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

    public double getLength() {
        return length;
    }

    public String getEdgeID() {
        return EdgeID;
    }

    public Waypoint getFrom() {
        return sourceWaypoint;
    }

    public Waypoint getTo() {
        return destinationWaypoint;
    }
}
