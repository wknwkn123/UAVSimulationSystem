package uav;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UAVFleetOwner {
    private static int idCount = 1;
    private int Id;
    private String name;
    private int operatorLicenseNumber;
    private Date operatorExpiryDate;
    private List<UAV> UAVs = new ArrayList<>();
    private int fleetSize;

    //constructor
    public UAVFleetOwner(String fleetOwnerName, int license_number, Date operator_expiry_date) {
        name = fleetOwnerName;
        operatorLicenseNumber = license_number;
        operatorExpiryDate = operator_expiry_date;
        fleetSize = UAVs.size();
        Id = idCount;
        idCount++;
    }

    //class methods
    public void addUAV(UAV new_uav) {
        UAVs.add(new_uav);
    }


    //getters
    public String getName() {
        return name;
    }

    public int getOperatorLicenseNumber() {
        return operatorLicenseNumber;
    }

    public Date getOperatorExpiryDate() {
        return operatorExpiryDate;
    }

    public List<Integer> getUAVs() {
        List<Integer> namesOfUAVs = new ArrayList<>();
        for (int i = 0; i < UAVs.size(); i++) {
            namesOfUAVs.add(UAVs.get(i).getUAVInfo().getId());
        }
        return namesOfUAVs;
    }

    public int getFleetSize() {
        return fleetSize;
    }

    public int getId() {
        return Id;
    }

}

