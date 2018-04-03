package uav;

import simulationengine.SimulationConfiguration;

import java.util.ArrayList;
import java.util.List;

public class RandomUAVCreator implements UAVCreator {

    public List<UAV> createUAVs(int number) {
        List<UAV> UAVs = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            UAVs.add(new UAV("DJI", "Drone", 50, 200, 5));
            System.out.println("UAV " + UAVs.get(i).getUAVInfo().getId() + " initialized");
        }
        return UAVs;
    }
}
