package websocket.simple_v2.encoder;

import com.google.gson.Gson;
import uav.UAVJSON;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<UAVJSON> {

    private static Gson gson = new Gson();

    @Override
    public UAVJSON decode(String s) throws DecodeException {
        return gson.fromJson(s, UAVJSON.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
