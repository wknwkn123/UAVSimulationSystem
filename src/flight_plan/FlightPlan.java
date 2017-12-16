package flight_plan;

import airspace_engine.waypoint.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class FlightPlan {
    private Waypoint startPoint;
    private Waypoint endPoint;
//    private DateTime targetStartTime;
//    private DateTime targetEndTime;
    private List<FlightSegment> flightPath = new ArrayList<>();
    private boolean completed;
    private double payload;
    private double progress;

    public FlightPlan(Waypoint start_point, Waypoint end_point, double load) {
        startPoint = start_point;
        endPoint = end_point;
        completed = false;
        this.payload = load;
        this.setProgress(0);
    }

    public List<FlightSegment> getFlightPath() {
        return flightPath;
    }

    public void addFlightSegment(FlightSegment segment) {
        flightPath.add(segment);
    }

    public Waypoint getStartPoint() {
        return startPoint;
    }

    public Waypoint getEndPoint() {
        return endPoint;
    }

    public boolean getCompleted() {
        return completed;
    }

    public double getPayload() {
        return payload;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }



}
