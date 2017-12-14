package uav;
import java.util.Date;
import uav.UAVInfo;

public class UAV {
    private UAVInfo UAVInfo;
    private UAVOperation UAVOperation;

    //constructor
    public UAV(String uav_name, String uav_owner, String uav_manufacturer, String model_type, double max_speed, double max_altitude, Date last_certification_date) {
        UAVInfo = new UAVInfo(uav_name, uav_owner, uav_manufacturer, model_type, max_speed, max_altitude, last_certification_date);
    }

    public void printUAVInfo() {
        System.out.println("ID: " + UAVInfo.getId());
        System.out.println("Name: " + UAVInfo.getName());
        System.out.println("Owner: " + UAVInfo.getOwner());
        System.out.println("Manufacturer: " + UAVInfo.getManufacturer());
        System.out.println("Model type: " + UAVInfo.getModelType());
        System.out.println("Maximum speed: " + UAVInfo.getMaxSpeed());
        System.out.println("Maximum altitude: " + UAVInfo.getMaxAltitude());
        System.out.println("Last certification date: " + UAVInfo.getLastCertificationDate());
    }

    public UAVInfo getUAVInfo() {
        return UAVInfo;
    }
}
