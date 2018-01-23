package test.collisionavoidanceengine;

import collisionavoidanceengine.FlightPlanScheduler;
import collisionavoidanceengine.request.Request;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1000","WP_BBM6","WP_RHM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A1() {
        // Passed
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1000","WP_W12M","WP_C28M",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A2() {
        // Passed
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1006","WP_PL62","WP_KM3",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test short route
    void scheduleSingleFlight_A3() {
        // Passed?
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1005","WP_KEM1","WP_PL16",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }



    @Test
    // Test extremely long route
    void scheduleSingleFlight_B() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1001","WP_J75M","WP_TM7",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
        // Test long route
    void scheduleSingleFlight_B1() {
        // Passed
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1004","WP_J75M","WP_PM12",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
    // Test the routing when there are two reqeust with closed time and OD pair
    void scheduleSingleFlight_C1C2() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        Request req = new Request("RQ_1002","WP_CK34","WP_C22M",2);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");

        Request req1 = new Request("RQ_1003","WP_CK34","WP_C28M",4);
        System.out.printf("Time needed is :"+fps.doModifiedAStar(req,0)+"\n");
    }

    @Test
    void scheduleFlight() {
        FlightPlanScheduler fps = new FlightPlanScheduler("PLANAR","RANDOM");
        fps.ScheduleFlight();
    }

}