package uav;

import java.util.Date;

public class UAVInfo {
    private static int idCount = 1;
    private String id;
    private String manufacturer;
    private String modelType;
    private double maxSpeed;
    private double maxAltitude;

    public UAVInfo(String uav_manufacturer, String model_type, double max_speed, double max_altitude) {
        manufacturer = uav_manufacturer;
        modelType = model_type;
        maxSpeed = max_speed;
        maxAltitude = max_altitude;
        id = "UV_" + String.format("%05d", idCount);
        idCount++;
    }

    public static int getIdCount() {
        return idCount;
    }

    public String getId() {
        return id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxAltitude() {
        return maxAltitude;
    }

    public void setMaxAltitude(double maxAltitude) {
        this.maxAltitude = maxAltitude;
    }

}
