package flight_plan;

import airspace_engine.airspace_structure.AirspaceStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomFlightPlanCreator implements FlightPlanCreator {

    public List<FlightPlan> createFlightPlans(AirspaceStructure airMap) {
        List<FlightPlan> flightPlans = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 25; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 25);
            int randomNum1 = ThreadLocalRandom.current().nextInt(0, 25);
            double load = 0.1 + r.nextDouble() * (2.9);
            while(randomNum == randomNum1) {
                randomNum1 = ThreadLocalRandom.current().nextInt(0, 25);
            }
            flightPlans.add(new FlightPlan(airMap.getNodes().get(randomNum), airMap.getNodes().get(randomNum1), load));
            System.out.println(flightPlans.get(i) + " initialized, starting at node " + flightPlans.get(i).getStartPoint().getX() + " ending at node " + flightPlans.get(i).getEndPoint().getX() + " with load of " + flightPlans.get(i).getPayload());
        }
        return flightPlans;
    }

}
