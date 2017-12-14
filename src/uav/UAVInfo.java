package uav;

import java.util.Date;

public class UAVInfo {
    private static int idCount = 1;
    private int Id;
    private String name;
    private String owner;
    private String manufacturer;
    private String modelType;
    private double maxSpeed;
    private double maxAltitude;
    private Date lastCertificationDate;

    public UAVInfo(String uav_name, String uav_owner, String uav_manufacturer, String model_type, double max_speed, double max_altitude, Date last_certification_date) {
        name = uav_name;
        owner = uav_owner;
        manufacturer = uav_manufacturer;
        modelType = model_type;
        maxSpeed = max_speed;
        maxAltitude = max_altitude;
        lastCertificationDate = last_certification_date;
        Id = idCount;
        idCount++;
    }

    public static int getIdCount() {
        return idCount;
    }

    public static void setIdCount(int idCount) {
        UAVInfo.idCount = idCount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Date getLastCertificationDate() {
        return lastCertificationDate;
    }

    public void setLastCertificationDate(Date lastCertificationDate) {
        this.lastCertificationDate = lastCertificationDate;
    }
}
