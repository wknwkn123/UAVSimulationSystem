package flight_plan;


import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.Flight;
import collisionavoidanceengine.flightplan.FlightSchedule;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import simulationengine.SimulationConfiguration;
import uav.UAV;
import uav.UAVEngine;

import java.util.ArrayList;
import java.util.List;

public class FlightPlanEngine {
    private List<Flight> flights = new ArrayList<>();
    private List<FlightPlan> flightPlans;
    private FlightSchedule currentFlightPlan;
    private UAVEngine uavEngine;
    private int numberOfUAVs;

    public FlightPlanEngine() {
        uavEngine = new UAVEngine();
    }

    public void createFlightPlans(String type, AirspaceStructure airMap) {
        switch(type) {
            case "RANDOM":
                FlightPlanCreator flightPlanCreator = FlightPlanFactory.getFlightPlanCreator("RANDOM");
//                setCurrentFlightPlan(flightPlanCreator.createFlightPlans(airMap));
                setFlights(flightPlanCreator.createFlightPlans(airMap));
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
                for (FlightPlan plan :  this.getFlightPlans()) {
                    UAV uav = uavEngine.getUAVs().get(i % this.numberOfUAVs);
                    uav.addJob(plan);
                    System.out.println("Job " + plan.getId() + " is assigned to UAV " + uav.getUAVInfo().getId());
                    i++;
                }
                break;
            case "RANDOMPLAN":
                int j = 0;
                for (Flight plan :  this.getFlights()) {
                    UAV uav = uavEngine.getUAVs().get(j);
                    uav.addFlightPlan(plan);
                    System.out.println("Job " + plan.getFlightID() + " is assigned to UAV " + uav.getUAVInfo().getId());
                    j++;
                }
                break;
            default:
                System.out.println("Default printed. This should not happen.");

        }
    }

    public FlightSchedule getCurrentFlightPlan() {
        return currentFlightPlan;
    }

    public void setCurrentFlightPlan(FlightSchedule currentFlightPlan) {
        this.currentFlightPlan = currentFlightPlan;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public UAVEngine getUavEngine() { return uavEngine; }

    public void setNumberOfUAVs(int numberOfUAVs) {
        this.numberOfUAVs = numberOfUAVs;
    }

    public int getNumberOfUAVs() {
        return numberOfUAVs;
    }
}
