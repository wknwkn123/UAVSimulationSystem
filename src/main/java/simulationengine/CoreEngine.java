package simulationengine;

import websocket.servers.server.HTTPServer;
import websocket.servers.server.Websocket;

/**
 * Entry point for the application.
 */

public class CoreEngine {

    public static void main(String[] args) {
        //start websocket server
        Websocket.getInstance().startServer();

        //start http-server for airspace editor
        HTTPServer.getInstance().startServer();
    }
}
