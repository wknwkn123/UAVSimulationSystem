package flight_plan;

import airspace_engine.airspace_structure.AirspaceStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFlightPlanCreator implements FlightPlanCreator {
    List<FlightPlan> flightPlans = new ArrayList<>();

    public List<FlightPlan> createFlightPlans(AirspaceStructure airMap) {
        //hardcoding flight plans
        flightPlans.add(new FlightPlan(airMap.getNodes().get(0), airMap.getNodes().get(5), 5));
        flightPlans.add(new FlightPlan(airMap.getNodes().get(2), airMap.getNodes().get(4), 2));
        flightPlans.add(new FlightPlan(airMap.getNodes().get(3), airMap.getNodes().get(4), 2));
        flightPlans.add(new FlightPlan(airMap.getNodes().get(0), airMap.getNodes().get(2), 4));
        flightPlans.add(new FlightPlan(airMap.getNodes().get(0), airMap.getNodes().get(1), 3));

        //hardcoding flight path for each plan
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getNodes().get(0), airMap.getNodes().get(1)));
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getNodes().get(1), airMap.getNodes().get(2)));
        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getNodes().get(2), airMap.getNodes().get(5)));

        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getNodes().get(2), airMap.getNodes().get(5)));
        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getNodes().get(5), airMap.getNodes().get(4)));

        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getNodes().get(3), airMap.getNodes().get(2)));
        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getNodes().get(2), airMap.getNodes().get(4)));

        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getNodes().get(0), airMap.getNodes().get(3)));
        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getNodes().get(3), airMap.getNodes().get(2)));

        flightPlans.get(4).addFlightSegment(new FlightSegment(airMap.getNodes().get(0), airMap.getNodes().get(1)));
        return flightPlans;
    }

}
