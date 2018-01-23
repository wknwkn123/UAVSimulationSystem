package flight_plan;

import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.ArrayList;
import java.util.List;

public class RandomFlightPlanCreator implements FlightPlanCreator {
    List<FlightPlan> flightPlans = new ArrayList<>();

    public List<FlightPlan> createFlightPlans(AirspaceStructure airMap) {
        //hardcoding flight plans
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00002"), 5, 0, 5));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00003"), airMap.getNodes().getWaypointByID("WP_00005"), 2, 2, 4));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00004"), airMap.getNodes().getWaypointByID("WP_00006"), 2, 5, 8));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00003"), 4, 5, 9));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00002"), 3, 10, 13));

        //hardcoding flight path for each plan
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));

        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00006")));
        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));

        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00010")));
        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));

        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));
        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));

        flightPlans.get(4).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));
        return flightPlans;
    }

}
