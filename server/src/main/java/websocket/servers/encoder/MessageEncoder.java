package websocket.servers.encoder;

import com.google.gson.Gson;
import uav.UAV;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<UAV> {

    private static Gson gson = new Gson();

    @Override
    public String encode(UAV uav) {
        Gson gson = new Gson();
        String jsonUAVs = gson.toJson(uav.getJsonData());
        System.out.println("UAV JSON DATA = " + jsonUAVs);
        return jsonUAVs;
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
