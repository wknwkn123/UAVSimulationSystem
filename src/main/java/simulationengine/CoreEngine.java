package simulationengine;

import airspaceengine.AirspaceEngine;
import flight_plan.FlightPlan;
import flight_plan.FlightPlanEngine;
import uav.UAV;
import uav.UAVEngine;

import java.io.IOException;

public class CoreEngine {

    public static void main(String[] args) throws IOException {
        //create airspace
        AirspaceEngine.getInstance().createAirspace("RANDOM");

        //create UAVs
        UAVEngine.getInstance().createUAVs("RANDOM");

        //create schedule/demand
        FlightPlanEngine.getInstance().createFlightPlans("RANDOM", AirspaceEngine.getInstance().getAirMap());

        //assign schedule to UAVs
        int i = 0;
        for (FlightPlan plan :  FlightPlanEngine.getInstance().getFlightPlans()) {
            UAV uav = UAVEngine.getInstance().getUAVs().get(i % 5);
            uav.addJob(plan);
            System.out.println("Job " + plan.getId() + " is assigned to UAV " + uav.getUAVInfo().getId());
            i++;
        }

        FlightPlanEngine.getInstance().printPlanDetails();

        //run simulation
        UAVEngine.getInstance().startThread();
        Thread t = new Thread(SimulationApp.getInstance());
        t.start();
//        try {
//            TimeUnit.MILLISECONDS.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        UAVEncoder.getInstance().encode(UAVEngine.getInstance().getUAVs().get(0));
//        Drawing2D.getInstance().draw2D();
    }
}
