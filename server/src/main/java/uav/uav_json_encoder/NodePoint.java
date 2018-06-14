package uav.uav_json_encoder;

import uav.Coordinate;

public class NodePoint {
    private Coordinate coordinate;
    private String ID;

    public NodePoint(String id, Coordinate coordinate){
        this.coordinate = coordinate;
        this.ID = id;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
