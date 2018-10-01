package flightPlan;

import airspaceEngine.waypoint.Waypoint;

public class WayPoint_JSON {

    private String NodeID;    // In form of WP_ID
    private boolean isTransferable;
    private double x;
    private double y;
    private double z;

    public WayPoint_JSON(Waypoint way) {
        NodeID = way.getNodeID();
        this.isTransferable = way.isTransferable();
        x = way.getX();
        y = way.getY();
        z = way.getZ();
    }


//    public String getNodeID() {
//        return NodeID;
//    }
//
//    public boolean isTransferable() {
//        return isTransferable;
//    }
//
//    public double getX() {
//        return x;
//    }
//
//    public double getY() {
//        return y;
//    }
//
//    public double getZ() {
//        return z;
//    }
//
//    public void setNodeID(String nodeID) {
//        NodeID = nodeID;
//    }
//
//    public void setTransferable(boolean transferable) {
//        isTransferable = transferable;
//    }
//
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    public void setZ(double z) {
//        this.z = z;
//    }
}
