package websocket.simple_v2.model;

import com.google.gson.Gson;
import uav.UAVJSON;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<UAVJSON> {

    private static Gson gson = new Gson();

    @Override
    public String encode(UAVJSON message) throws EncodeException {
        return gson.toJson(message);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
