package uav.uav_json_encoder;

import airspaceengine.waypoint.Waypoint;
import simulationengine.Time;
import uav.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class UAVJSON {
    private String id;
    private String planID;
    private Coordinate coordinate;
    private double time;
    private NodePoint startPoint;
    private NodePoint endPoint;

    public Coordinate getCoordinate() { return coordinate; }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlanID(String planID) {
        this.planID = planID;
    }

    public String getPlanID() {
        return planID;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public NodePoint getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(NodePoint startPoint) {
        this.startPoint = startPoint;
    }

    public NodePoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(NodePoint endPoint) {
        this.endPoint = endPoint;
    }
}
