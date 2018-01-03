package collisionavoidanceengine;

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
    void scheduleFlight() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        fps.ScheduleFlight();
    }

}