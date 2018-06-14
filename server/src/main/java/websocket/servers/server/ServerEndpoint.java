package websocket.servers.server;

import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import simulationengine.SimulationApp;
import simulationengine.json_formatting.Simulation;

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