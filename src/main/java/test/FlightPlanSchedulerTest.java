package test;

import collisionavoidanceengine.FlightPlanScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 2/1/18.
 */
class FlightPlanSchedulerTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void constructor(){
        FlightPlanScheduler fpScheduler = new FlightPlanScheduler("Planner", "Random");
    }

//    @Test
//    void scheduleFlight() {
//        fpScheduler.ScheduleFlight();
//    }

}