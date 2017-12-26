package flight_plan;

import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.ArrayList;
import java.util.List;

public class RandomFlightPlanCreator implements FlightPlanCreator {
    List<FlightPlan> flightPlans = new ArrayList<>();

    public List<FlightPlan> createFlightPlans(AirspaceStructure airMap) {
        //hardcoding flight plans
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointList().get(0), airMap.getNodes().getWaypointList().get(5), 5, 0, 5));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointList().get(2), airMap.getNodes().getWaypointList().get(4), 2, 2, 4));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointList().get(3), airMap.getNodes().getWaypointList().get(4), 2, 5, 8));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointList().get(0), airMap.getNodes().getWaypointList().get(2), 4, 5, 9));
        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointList().get(0), airMap.getNodes().getWaypointList().get(1), 3, 10, 13));

        //hardcoding flight path for each plan
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(0)));
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(1)));
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(5)));

        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(5)));
        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(10)));

        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(6)));
        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(5)));
        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(8)));

        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(2)));
        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(6)));

        flightPlans.get(4).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegList().get(0)));
        return flightPlans;
    }

}
