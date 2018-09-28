package flightEngine;

import servers.HTTPServer;
//import websocket.servers.server.Websocket;

/**
 * Entry point for the application.
 */

public class Main {

    public static void main(String[] args) {

        //start http-server for airspace editor
        HTTPServer.getInstance().startServer();
    }
}
