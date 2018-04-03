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

        if (parameter.getSimulationStart().equalsIgnoreCase("start")){
            SimulationApp simApp = new SimulationApp(parameter);
            simApp.startSimulation();
        } else {
//            SimulationApp.getInstance().stopWork();
        }
    }

}