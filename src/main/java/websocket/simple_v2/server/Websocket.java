package websocket.simple_v2.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


public class Websocket {
    private static Websocket instance = new Websocket();
    private Session session;

    public Websocket() {};

    public static Websocket getInstance() {
        return instance;
    }

    public void startServer() {
        Server server = new Server(9000);
        WebSocketHandler wsHandler = new WebSocketHandler() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(ServerEndpoint.class);
            }
        };
        server.setHandler(wsHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            server.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}