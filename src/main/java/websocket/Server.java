package websocket;

import uav.UAVJSON;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(
        value="to sync with andre",
        decoders = MessageDecoder.class,
        encoders = MessageEncoder.class )
public class Server {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
    }

    @OnMessage
    public void onMessage(Session session, UAVJSON message) throws IOException {
        // Handle new messages
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        // WebSocket connection closes
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}