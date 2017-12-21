package uav;

import java.util.ArrayList;
import java.util.List;

public class UAVEngine {
    private static UAVEngine ourInstance = new UAVEngine();
    private List<UAV> UAVs;
    private List<Thread> UAVThread = new ArrayList<>();

    public static UAVEngine getInstance() {
        return ourInstance;
    }

    private UAVEngine() { }

    public void createUAVs(String type) {
        switch(type) {
            case "RANDOM":
                UAVCreator uavCreator = UAVFactory.getUAVCreator("RANDOM");
                setUAVs(uavCreator.createUAVs());
                for (UAV uav : UAVEngine.getInstance().getUAVs()) {
                    uav.setOrigin();
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
        for (Thread uav : UAVThread)
            uav.stop();
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
