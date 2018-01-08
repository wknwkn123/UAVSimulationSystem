package websocket.simple_v2.server;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import java.io.IOException;

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
        System.out.println("Connected.");
        Websocket.getInstance().setSession(session);


        RemoteEndpoint remote = session.getRemote();
    // Blocking Send of a TEXT message to remote endpoint
        String part1 = "Hello";
        String part2 = " World";
        try
        {
            remote.sendPartialString(part1,false);
            remote.sendPartialString(part2,true); // last part
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        System.out.println("Message: " + message);
    }

}