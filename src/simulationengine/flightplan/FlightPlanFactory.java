package simulationengine.flightplan;

public class FlightPlanFactory {
    public static FlightPlanCreator getFlightPlanCreator(String type) {
        switch(type) {
            case "RANDOM":
                return new RandomFlightPlanCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
