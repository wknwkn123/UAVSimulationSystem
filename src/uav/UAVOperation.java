package uav;

import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.Waypoint;

public class UAVOperation {
    private double currentX;
    private double currentY;
    // Ziji : For now, we just consider 2D case
    private double currentZ=10;

    // TODO : calculate the actural position given time, currentRouteSegment, and currentWayPoint.
    private RouteSegment currentRouteSegment;
    private Waypoint currentWayPoint;

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
