package simulation_engine;

import airspace_engine.AirspaceEngine;
import airspace_engine.Drawing2D;
import flight_plan.FlightPlan;
import flight_plan.FlightPlanEngine;
import uav.UAVEngine;

import javax.swing.*;

public class CoreEngine {

    public static void main(String[] args) {
        //create airspace
        AirspaceEngine.getInstance().createAirspace("RANDOM");

        Drawing2D.getInstance().draw2D();

//        //create UAVs
//        UAVEngine.getInstance().createUAVs("RANDOM");
//
//        //create schedule/demand
//        FlightPlanEngine.getInstance().createFlightPlans("RANDOM", AirspaceEngine.getInstance().getAirMap());
//
//        //assign schedule to UAVs
//        int i = 0;
//        for (FlightPlan plan :  FlightPlanEngine.getInstance().getFlightPlans()) {
//            UAVEngine.getInstance().getUAVs().get(i % 5).addJob(plan);
//            i++;
//        }
//
//        //run simulation
//        UAVEngine.getInstance().startThread();
//        Thread t = new Thread(SimulationApp.getInstance());
//        t.start();
    }
}
