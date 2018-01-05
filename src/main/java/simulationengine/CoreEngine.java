package simulationengine;

import airspaceengine.AirspaceEngine;
import flight_plan.FlightPlanEngine;
import uav.UAVEngine;
import websocket.simple_v2.server.Server;

import java.io.IOException;

public class CoreEngine {

    public static void main(String[] args) throws IOException {
        //create airspace
        AirspaceEngine.getInstance().createAirspace(SimulationConfiguration.getInstance().getAirspaceType());

        //create UAVs
        UAVEngine.getInstance().createUAVs("RANDOM");

        //create schedule/demand
        FlightPlanEngine.getInstance().createFlightPlans(SimulationConfiguration.getInstance().getFlightScheduleType(), AirspaceEngine.getInstance().getAirMap());

        //assign schedule to UAVs
        FlightPlanEngine.getInstance().assignFlightPlans("RANDOM");

        //run simulation
        UAVEngine.getInstance().startThread();
        Thread t = new Thread(SimulationApp.getInstance());
        t.start();

        //start websocket server
        Server.getInstance().startServer();
    }
}
