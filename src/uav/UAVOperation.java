package uav;

public class UAVOperation {
    private double currentX;
    private double currentY;
    private double currentZ;
    private double remainingBatteryLevel;

    public double getCurrentX() {
        return currentX;
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    public double getCurrentY() {
        return currentY;
    }

    public void setCurrentY(double currentY) {
        this.currentY = currentY;
    }

    public double getCurrentZ() {
        return currentZ;
    }

    public void setCurrentZ(double currentZ) {
        this.currentZ = currentZ;
    }

    public double getRemainingBatteryLevel() {
        return remainingBatteryLevel;
    }

    public void setRemainingBatteryLevel(double remainingBatteryLevel) {
        this.remainingBatteryLevel = remainingBatteryLevel;
    }
}
