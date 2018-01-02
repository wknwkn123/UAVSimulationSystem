package websocket.simple_v2.server;

import uav.UAVJSON;
import websocket.simple_v2.model.MessageDecoder;
import websocket.simple_v2.model.MessageEncoder;

import javax.websocket.*;
import java.io.IOException;

@javax.websocket.server.ServerEndpoint(value="/", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ServerEndpoint {
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