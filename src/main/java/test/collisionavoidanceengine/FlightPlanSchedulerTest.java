package collisionavoidanceengine;

import collisionavoidanceengine.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 3/1/18.
 */
class FlightPlanSchedulerTest {

    @Test
    void constructorTest() {
//        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
    }

    @Test
    void scheduleSingleFlight() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1000","WP_IABY","WP_RHM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0));
    }

    @Test
    void scheduleFlight() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        fps.ScheduleFlight();
    }

}