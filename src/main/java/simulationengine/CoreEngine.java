package simulationengine;

import airspaceengine.AirspaceEngine;
import collisionavoidanceengine.FlightPlanScheduler;
import collisionavoidanceengine.flightplan.Flight;
import config.Config;
import flight_plan.FlightPlanEngine;
import uav.UAVEngine;
import websocket.simple_v2.server.Websocket;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Entry point for the application.
 */

public class CoreEngine {

    public static void main(String[] args) throws IOException {
        //start websocket server
        Websocket.getInstance().startServer();
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        //create airspace
//        AirspaceEngine.getInstance().createAirspace(SimulationConfiguration.getInstance().getConfig());
//
//        //create UAVs
//        UAVEngine.getInstance().createUAVs("RANDOM");
//
//        //create schedule/demand
//        FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00001", "RQ_001", "UV_000001", 0, 5));
//        FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00002", "RQ_002", "UV_000002", 2, 6));
//        FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00003", "RQ_003", "UV_000003", 4, 10));
//        ArrayList<String> path1 = new ArrayList<>();
//        path1.add("WP_B85");
//        path1.add("WP_B97");
//        FlightPlanEngine.getInstance().getFlights().get(0).setFlightPath(path1);
//        ArrayList<String> path2 = new ArrayList<>();
//        path2.add("WP_THM");
//        path2.add("WP_GMLM");
//        FlightPlanEngine.getInstance().getFlights().get(1).setFlightPath(path2);
//        ArrayList<String> path3 = new ArrayList<>();
//        path2.add("WP_Y61M");
//        path2.add("WP_Y28M");
//        FlightPlanEngine.getInstance().getFlights().get(2).setFlightPath(path2);
//        FlightPlanEngine.getInstance().createFlightPlans(SimulationConfiguration.getInstance().getFlightScheduleType(), AirspaceEngine.getInstance().getAirMap());

//        FlightPlanScheduler scheduler = new FlightPlanScheduler(new Config());
//        scheduler.ScheduleFlight();

//        for (Map.Entry<String, Flight> entry : scheduler.currentFlightPlan.getFlightPlan().entrySet())
//        {
//            System.out.println(entry.getKey() + "/" + entry.getValue());
//        }

//        //assign schedule to UAVs
//        FlightPlanEngine.getInstance().assignFlightPlans("RANDOMPLAN");
//
//        //run simulation
//        UAVEngine.getInstance().startThread();
//        Thread t = new Thread(SimulationApp.getInstance());
//        t.start();
    }
}
