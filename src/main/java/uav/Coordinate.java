package uav;

import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    private List<String> coor = new ArrayList<>();

    public Coordinate(double x, double y, double z) {
        coor.add(String.valueOf(x));
        coor.add(String.valueOf(y));
        coor.add(String.valueOf(z));
    }

    public List<String> getCoor() {
        return coor;
    }
}
