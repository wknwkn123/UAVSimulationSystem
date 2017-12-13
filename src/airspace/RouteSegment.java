package airspace;

public class RouteSegment {

    //instance variables
    private final Waypoint from;
    private final Waypoint to;
    private double targetSpeed;
    private double actualSpeed;
    private final int weight;

    //constructor
    public RouteSegment(Waypoint origin, Waypoint end, int weight) {
        this.from = origin;
        this.to = end;
        this.weight = weight;
    }

    //getter methods
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
