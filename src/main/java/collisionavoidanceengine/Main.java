package collisionavoidanceengine;

import collisionavoidanceengine.request.Request;
import config.Config;

/**
 * Created by Ziji Shi on 24/1/18.
 */
public class Main {
    public static void main(){

        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1006","WP_PL62","WP_KM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");

    }


}
