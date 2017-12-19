package uav;

import java.util.List;

public class UAVEngine {
    private static UAVEngine ourInstance = new UAVEngine();
    private List<UAV> UAVs;

    public static UAVEngine getInstance() {
        return ourInstance;
    }

    private UAVEngine() { }

    public void createUAVs(String type) {
        switch(type) {
            case "RANDOM":
                UAVCreator uavCreator = UAVFactory.getUAVCreator("RANDOM");
                setUAVs(uavCreator.createUAVs());
                break;
            default:
                System.out.println("Default is printed. This should not happen.");
                break;
        }
    }

    public List<UAV> getUAVs() {
        return UAVs;
    }

    public void setUAVs(List<UAV> UAVs) {
        this.UAVs = UAVs;
    }
}
