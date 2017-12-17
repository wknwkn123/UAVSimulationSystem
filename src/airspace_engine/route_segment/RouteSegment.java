package airspace_engine.route_segment;

import airspace_engine.waypoint.Waypoint;

public class RouteSegment {

    //instance variables
    private final String id;
    private final Waypoint from;
    private final Waypoint to;
    private double targetSpeed;
    private double actualSpeed;
    private final int weight;
    private static int routeSegmentID = 0;

    //constructor
    public RouteSegment(Waypoint origin, Waypoint end, int weight) {
        this.from = origin;
        this.to = end;
        this.weight = weight;
        id = "RS" + String.format("%05d", routeSegmentID);
        routeSegmentID++;
    }

    //getter methods


    public String getId() { return id; }

    public Waypoint getFrom() {
        return from;
    }

    public Waypoint getTo() {
        return to;
    }

    public double getTargetSpeed() {
        return targetSpeed;
    }

    public double getActualSpeed() {
        return actualSpeed;
    }

    public int getWeight() {
        return weight;
    }

}
