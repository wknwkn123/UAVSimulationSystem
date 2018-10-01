package flightPlan;

import airspaceEngine.routesegment.RouteSegment;

public class FlightSegment {
    private RouteSegment segment;

    public FlightSegment(RouteSegment segment) {
        this.segment = segment;
    }

    public RouteSegment getSegment() {
        return segment;
    }
}
