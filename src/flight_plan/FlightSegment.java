package flight_plan;

import airspace_engine.route_segment.RouteSegment;
import airspace_engine.waypoint.Waypoint;

public class FlightSegment {
    private RouteSegment segment;

    public FlightSegment(RouteSegment segment) {
        this.segment = segment;
    }

    public RouteSegment getSegment() {
        return segment;
    }
}
