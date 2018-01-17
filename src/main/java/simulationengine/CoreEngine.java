package simulationengine;

import airspaceengine.AirspaceEngine;
import collisionavoidanceengine.FlightPlanScheduler;
import flight_plan.FlightPlanEngine;
import uav.UAVEngine;
import websocket.simple_v2.server.Websocket;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class CoreEngine {

    public static void main(String[] args) throws IOException {
        //start websocket server
//        Websocket.getInstance().startServer();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        //create airspace
//        AirspaceEngine.getInstance().createAirspace(SimulationConfiguration.getInstance().getAirspaceType());

//        //create UAVs
//        UAVEngine.getInstance().createUAVs("RANDOM");
//
//        //create schedule/demand
//        FlightPlanEngine.getInstance().createFlightPlans(SimulationConfiguration.getInstance().getFlightScheduleType(), AirspaceEngine.getInstance().getAirMap());
//
//        //assign schedule to UAVs
//        FlightPlanEngine.getInstance().assignFlightPlans("RANDOM");
//
//        //run simulation
//        UAVEngine.getInstance().startThread();
//        Thread t = new Thread(SimulationApp.getInstance());
//        t.start();

        FlightPlanScheduler scheduler = new FlightPlanScheduler("PLANAR","RANDOM");
        scheduler.ScheduleFlight();
    }
}
