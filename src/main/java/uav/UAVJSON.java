package uav;

import java.util.ArrayList;
import java.util.List;

public class UAVJSON {
    private String id;
    private String planID;
    private List<Coordinate> coordinateList = new ArrayList<>();

    public List<Coordinate> getCoordinateList() {
        return coordinateList;
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
}
