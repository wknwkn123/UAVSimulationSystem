package simulationengine.flightplan;

import java.util.ArrayList;
import java.util.List;

import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.Waypoint;

public class FlightPlan {
    private Waypoint startPoint;
    private Waypoint endPoint;
    private List<Waypoint> nodes = new ArrayList<Waypoint>();
    private List<RouteSegment> edges = new ArrayList<RouteSegment>();
//    private DateTime targetStartTime;
//    private DateTime targetEndTime;
//    private DateTime actualStartTime;
//    private DateTime actualEndTime;
    private List<Waypoint> flightPos = new ArrayList<Waypoint>();
    private boolean completed;
    private double payload;
    private double progress;

    //constructor
    public FlightPlan(Waypoint start_point, Waypoint end_point, double load) {
        startPoint = start_point;
        endPoint = end_point;
        //targetStartTime = target_start_time;
        //targetEndTime = target_end_time;
        flightPos.add(start_point);
        completed = false;
        this.payload = load;
//		nodes.add(start_point);
        this.setProgress(0);
    }

    //getter
    public Waypoint getStartPoint() {
        return startPoint;
    }

    public Waypoint getEndPoint() {
        return endPoint;
    }

    public List<Waypoint> getNodes() {
        return nodes;
    }

    public List<RouteSegment> getEdges() {
        return edges;
    }

//	public DateTime getTargetStartTime() {
//		return targetStartTime;
//	}
//
//	public DateTime getTargetEndTime() {
//		return targetEndTime;
//	}
//
//	public DateTime getActualStartTime() {
//		return actualStartTime;
//	}
//
//	public DateTime getActualEndTime() {
//		return actualEndTime;
//	}

    public List<Waypoint> getFlightPos() {
        return flightPos;
    }

    public void setNodes(List<Waypoint> nodes) {
        for (int i = 0; i < nodes.size(); i++) {
            this.nodes.add(nodes.get(i));
        }
    }

    public void addNode(Waypoint node) {
        this.nodes.add(node);
    }

    public void addEdge(RouteSegment edge) {
        this.edges.add(edge);
    }

    public boolean getCompleted() {
        return completed;
    }

    public double getPayload() {
        return payload;
    }

    //setter
    public void setFlightPos(Waypoint waypoint) {
        this.flightPos.add(waypoint);
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

    public double getTotalDistance() {
        double total = 0;
        for (RouteSegment edge: this.edges) {
            total += edge.getLength();
        }
        return total;
    }

}
