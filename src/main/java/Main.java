import collisionavoidanceengine.FlightPlanScheduler;

/**
 * Created by Ziji Shi on 4/1/18.
 *
 * Overall execution control class.
 *
 */
public class Main {

    public static void main (String[] args){
        String inPathToMapFile = null;      // path to input file
        String outPathToRequestFile = null; // path to output
        FlightPlanScheduler scheduler = new FlightPlanScheduler("PLANAR","RANDOM");
        scheduler.ScheduleFlight();
    }
}
