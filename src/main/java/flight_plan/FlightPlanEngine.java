package flight_plan;


import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.Flight;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import uav.UAV;
import uav.UAVEngine;

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

    public void printPlanDetails() {
        for (FlightPlan plan : this.flightPlans) {
            System.out.println("Plan ID: " + plan.getId());
            System.out.println("Start time: " + plan.getTargetStartTime());
            System.out.println("End time: " + plan.getTargetEndTime());
            System.out.println("Origin: " + plan.getStartPoint());
            System.out.println("Destination: " + plan.getEndPoint());
        }
    }

    public void setFlightPlans(List<FlightPlan> flightPlans) {
        this.flightPlans = flightPlans;
    }

    public List<FlightPlan> getFlightPlans() {
        return flightPlans;
    }

    public void assignFlightPlans(String type) {
        switch (type) {
            case "RANDOM":
                int i = 0;
                for (FlightPlan plan :  FlightPlanEngine.getInstance().getFlightPlans()) {
                    UAV uav = UAVEngine.getInstance().getUAVs().get(i % 5);
                    uav.addJob(plan);
                    System.out.println("Job " + plan.getId() + " is assigned to UAV " + uav.getUAVInfo().getId());
                    i++;
                }
                break;
            default:
                System.out.println("Default printed. This should not happen.");

        }
    }
}
