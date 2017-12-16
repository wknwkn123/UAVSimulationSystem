package flight_plan;

import airspace_engine.waypoint.Waypoint;

public class FlightSegment {
    private Waypoint origin;
    private Waypoint destination;

    public FlightSegment(Waypoint origin, Waypoint destination) {
        this.origin = origin;
        this.destination = destination;

    }

    public Waypoint getOrigin() {
        return origin;
    }

    public void setOrigin(Waypoint origin) {
        this.origin = origin;
    }

    public Waypoint getDestination() {
        return destination;
    }

    public void setDestination(Waypoint destination) {
        this.destination = destination;
    }
}
