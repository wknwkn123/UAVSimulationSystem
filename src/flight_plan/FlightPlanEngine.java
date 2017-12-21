package flight_plan;


import airspace_engine.airspace_structure.AirspaceStructure;

import java.util.List;

public class FlightPlanEngine {
    private static FlightPlanEngine ourInstance = new FlightPlanEngine();
    private List<FlightPlan> flightPlans;

    public static FlightPlanEngine getInstance() {
        return ourInstance;
    }

    private FlightPlanEngine() { }

    public void createFlightPlans(String type, AirspaceStructure airMap) {
        switch(type) {
            case "RANDOM":
                FlightPlanCreator flightPlanCreator = FlightPlanFactory.getFlightPlanCreator("RANDOM");
                setFlightPlans(flightPlanCreator.createFlightPlans(airMap));
                break;
            default:
                System.out.println("Default is printed. This should not happen.");
                break;
        }
    }

    public void setFlightPlans(List<FlightPlan> flightPlans) {
        this.flightPlans = flightPlans;
    }

    public List<FlightPlan> getFlightPlans() {
        return flightPlans;
    }
}
