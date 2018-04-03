package uav;

import simulationengine.SimulationConfiguration;

import java.util.ArrayList;
import java.util.List;

public class UAVEngine {
    private List<UAV> UAVs;
    private List<Thread> UAVThread = new ArrayList<>();
    private SimulationConfiguration config;

    public UAVEngine(SimulationConfiguration configuration) {
        this.config = configuration;
    }

    public void createUAVs(String type) {
        switch(type) {
            case "RANDOM":
                UAVCreator uavCreator = UAVFactory.getUAVCreator("RANDOM");
                setUAVs(uavCreator.createUAVs(this.config.getNumberOfFlights()));
                for (UAV uav : this.getUAVs()) {
                    UAVThread.add(new Thread(uav));
                }
                break;
            default:
                System.out.println("Default is printed. This should not happen.");
                break;
        }
    }

    public void startThread() {
        for (Thread uav : UAVThread)
            uav.start();
    }

    public void stopThread() {
        for (UAV uav : UAVs)
            uav.stopWork();
    }


    public List<UAV> getUAVs() {
        return UAVs;
    }

    public void setUAVs(List<UAV> UAVs) {
        this.UAVs = UAVs;
    }

    public List<Thread> getUAVThread() {
        return UAVThread;
    }
}
