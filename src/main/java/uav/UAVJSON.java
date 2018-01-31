package uav;

import simulationengine.Time;

import java.util.ArrayList;
import java.util.List;

public class UAVJSON {
    private String id;
    private String planID;
    private Coordinate coordinate;
    private double time;
    private Coordinate startPoint;
    private Coordinate endPoint;

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

    public Coordinate getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Coordinate startPoint) {
        this.startPoint = startPoint;
    }

    public Coordinate getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Coordinate endPoint) {
        this.endPoint = endPoint;
    }
}
