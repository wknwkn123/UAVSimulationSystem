import collisionavoidanceengine.FlightPlanScheduler;

/**
 * Created by Ziji Shi on 4/1/18.
 *
 * Overall execution control class.
 *
 */
public class Main {

    public static void main (String[] args){

        String inPathRoot = "data/in";
        String inPathToMapFile = inPathRoot;      // path to input file

        String outPathRoot = "data/out";
        String outPathToRequestFile = outPathRoot+"/Request.csv"; // path to output
        String outPathToScheduleFile = outPathRoot+"/Schedule";

        FlightPlanScheduler scheduler = new FlightPlanScheduler("PLANAR","RANDOM");
        scheduler.ScheduleFlight();
    }
}
