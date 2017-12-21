package simulationengine.flightplan;

import airspaceengine.airspacestructure.AirspaceStructure;

public class RandomFlightPlanCreator implements FlightPlanCreator {

    public void createFlightPlans(AirspaceStructure airMap) {
        // TODO : Put it back
//        List<FlightPlan> flightPlans = new ArrayList<>();
//        Random r = new Random();
//        for (int i = 0; i < 25; i++) {
//            int randomNum = ThreadLocalRandom.current().nextInt(0, 25);
//            int randomNum1 = ThreadLocalRandom.current().nextInt(0, 25);
//            double load = 0.1 + r.nextDouble() * (2.9);
//            while(randomNum == randomNum1) {
//                randomNum1 = ThreadLocalRandom.current().nextInt(0, 25);
//            }
//            flightPlans.add(new FlightPlan(airMap.getNodes().get(randomNum), airMap.getNodes().get(randomNum1), load));
//            System.out.println(flightPlans.get(i) + " initialized, starting at node " + flightPlans.get(i).getStartPoint().getX() + " ending at node " + flightPlans.get(i).getEndPoint().getX() + " with load of " + flightPlans.get(i).getPayload());
//        }
//        return flightPlans;
    }

}
