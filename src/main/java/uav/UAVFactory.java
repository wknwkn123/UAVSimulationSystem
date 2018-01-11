package uav;

public class UAVFactory {
    public static UAVCreator getUAVCreator(String type) {
        switch(type) {
            case "RANDOM":
                return new RandomUAVCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
