package uav;

import java.util.ArrayList;
import java.util.List;

public class RandomUAVCreator implements UAVCreator {

    public List<UAV> createUAVs() {
        List<UAV> UAVs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UAVs.add(new UAV("DJI", "Drone", 50, 200));
            System.out.println("UAV " + UAVs.get(i).getUAVInfo().getId() + " initialized");
        }
        return UAVs;
    }
}
