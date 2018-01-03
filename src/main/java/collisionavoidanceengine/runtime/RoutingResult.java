package collisionavoidanceengine.runtime;

import java.util.List;

/**
 * Created by Ziji Shi on 1/1/18.
 *
 * Different from the FlightPlan, which is based on individual node and list;
 * this is sorted on flightID
 */
public class RoutingResult {
    private String flightID=null;
    private double startTime;
    private double tripTime;
    private List<String> flightPath;

    public RoutingResult(String id, double startTime, double tripTime, List<String> flightPath ){
        this.flightID=id;
        this.startTime=startTime;
        this.tripTime = tripTime;
        this.flightPath=flightPath;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public double getTripTime() {
        return tripTime;
    }

    public void setTripTime(double tripTime) {
        this.tripTime = tripTime;
    }

    public List<String> getFlightPath() {
        return flightPath;
    }

    public String getFlightID() {
        return flightID;
    }
}
