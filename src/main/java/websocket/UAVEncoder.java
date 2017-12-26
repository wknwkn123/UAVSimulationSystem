package websocket;

import com.google.gson.Gson;
import uav.UAV;

public class UAVEncoder {

    private static UAVEncoder instance = new UAVEncoder();

    public static UAVEncoder getInstance() {
        return instance;
    }

    public void encode(final UAV uav) {
        Gson gson = new Gson();
        String jsonUAVs = gson.toJson(uav.getJsonData());
        System.out.println("UAV JSON DATA = " + jsonUAVs);
//        return jsonUAVs;
    }

}