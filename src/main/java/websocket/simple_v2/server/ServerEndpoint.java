package websocket.simple_v2.server;

import airspaceengine.AirspaceEngine;
import collisionavoidanceengine.flightplan.Flight;
import com.google.gson.Gson;
import flight_plan.FlightPlanEngine;
import jdk.nashorn.internal.parser.JSONParser;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.json.JSONObject;
import simulationengine.SimulationApp;
import simulationengine.SimulationConfiguration;
import simulationengine.json_formatting.Simulation;
import uav.UAVEngine;

import java.io.IOException;
import java.util.ArrayList;

@org.eclipse.jetty.websocket.api.annotations.WebSocket
public class ServerEndpoint {

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        System.out.println("A client has connected.");
        Websocket.getInstance().setSession(session);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        Gson gson = new Gson();
        Simulation parameter = gson.fromJson(message, Simulation.class);
//        System.out.println(message);
//        System.out.println();
//        System.out.println(parameter.getParameter().getAirspaceType());
//        System.out.println();
//        System.out.println(parameter.getParameter().getFlightScheduleType());
//        System.out.println();
//        System.out.println(parameter.getSimulationStart());

        if (parameter.getSimulationStart().equalsIgnoreCase("start")){
            SimulationConfiguration.getInstance().setAirspaceType(parameter.getParameter().getAirspaceType());
            SimulationConfiguration.getInstance().setFlightScheduleType(parameter.getParameter().getFlightScheduleType());

            //starting simulation
            // create airspace
            try {
                AirspaceEngine.getInstance().createAirspace(SimulationConfiguration.getInstance().getConfig());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //create uavs
            UAVEngine.getInstance().createUAVs("RANDOM");

            //create schedule/demand
            FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00001", "RQ_001", "UV_000001", 0, 5));
            FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00002", "RQ_002", "UV_000002", 2, 6));
            FlightPlanEngine.getInstance().getFlights().add(new Flight("FL_00003", "RQ_003", "UV_000003", 4, 10));
            ArrayList<String> path1 = new ArrayList<>();
            path1.add("WP_B85");
            path1.add("WP_B97");
            FlightPlanEngine.getInstance().getFlights().get(0).setFlightPath(path1);
            ArrayList<String> path2 = new ArrayList<>();
            path2.add("WP_THM");
            path2.add("WP_GMLM");
            FlightPlanEngine.getInstance().getFlights().get(1).setFlightPath(path2);
            ArrayList<String> path3 = new ArrayList<>();
            path2.add("WP_Y61M");
            path2.add("WP_Y28M");
            FlightPlanEngine.getInstance().getFlights().get(2).setFlightPath(path2);

            //assign schedule to UAVs
            FlightPlanEngine.getInstance().assignFlightPlans("RANDOMPLAN");

            UAVEngine.getInstance().startThread();
            Thread t = new Thread(SimulationApp.getInstance());
            t.start();
        } else {
            UAVEngine.getInstance().stopThread();
        }
    }

}