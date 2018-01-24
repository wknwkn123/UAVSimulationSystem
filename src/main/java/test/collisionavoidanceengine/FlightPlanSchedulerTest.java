package test.collisionavoidanceengine;

import collisionavoidanceengine.FlightPlanScheduler;
import collisionavoidanceengine.request.Request;
import config.Config;
import org.junit.jupiter.api.Test;

/**
 * Created by Ziji Shi on 3/1/18.
 */
class FlightPlanSchedulerTest {

    @Test
    void constructorTest() {
        // FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A() {
        // Passed ?
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1000","WP_BBM6","WP_RHM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A1() {
        // Passed
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1000","WP_JM1","WP_KEM1",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A2() {
        // Passed
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1006","WP_PL62","WP_KM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A3() {
        // Passed?
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1005","WP_KEM1","WP_PL16",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }



    @Test
    // Test extremely long route
    void scheduleSingleFlight_B() {
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1001","WP_J75M","WP_TM7",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test long route
    void scheduleSingleFlight_B1() {
        // Passed
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1004","WP_J75M","WP_PM12",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
    // Test the routing when there are two reqeust with closed time and OD pair
    void scheduleSingleFlight_C1C2() {
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        Request req = new Request("RQ_1002","WP_CK34","WP_C22M",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");

        Request req1 = new Request("RQ_1003","WP_CK34","WP_C28M",4);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
    void scheduleFlight() {
        Config config = new Config();
        FlightPlanScheduler fps = new FlightPlanScheduler(config);
        fps.ScheduleFlight();
    }

}