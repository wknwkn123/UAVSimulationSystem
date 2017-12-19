package collision_avoidance_engine;

/**
 * Created by StevenShi on 18/12/17.
 *
 * Request to add a delivery flight to existing schedule
 *
 *
 */
public class Request {
    private int origin;
    private int destination;
    private int startTime;

    public int getOrigin(){
        return this.origin;
    }

    public int getDestination(){
        return this.destination;
    }

    public int getStartTime(){
        return this.startTime;
    }
}
