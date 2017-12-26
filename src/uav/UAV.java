package uav;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import airspace_engine.waypoint.Waypoint;
import flight_plan.FlightPlan;
import flight_plan.FlightSegment;
import simulation_engine.Time;
import uav.UAVInfo;

public class UAV implements Runnable{
    private UAVInfo UAVInfo;
    private UAVOperation operation;
    private List<FlightPlan> schedule;
    private volatile boolean stopWork;;
    private boolean done;

    //constructor
    public UAV(String uav_manufacturer, String model_type, double max_speed, double max_altitude) {
        UAVInfo = new UAVInfo(uav_manufacturer, model_type, max_speed, max_altitude);
        schedule = new ArrayList<>();
        operation = new UAVOperation();
    }

    public void printUAVInfo() {
        System.out.println("ID: " + UAVInfo.getId());
        System.out.println("Manufacturer: " + UAVInfo.getManufacturer());
        System.out.println("Model type: " + UAVInfo.getModelType());
        System.out.println("Maximum speed: " + UAVInfo.getMaxSpeed());
        System.out.println("Maximum altitude: " + UAVInfo.getMaxAltitude());
    }

    public UAVInfo getUAVInfo() {
        return UAVInfo;
    }

    public void addJob(FlightPlan job) {
        schedule.add(job);
    }

    public void setOrigin() {
        if (schedule.size() > 0) {
            operation.setCurrentX(schedule.get(0).getStartPoint().getX());
            operation.setCurrentY(schedule.get(0).getStartPoint().getY());
            operation.setCurrentZ(schedule.get(0).getStartPoint().getZ());
        }
    }

    public void run() {
        while(!stopWork && !done) {
            while(!Time.getInstance().isCompleted()) {
                if (schedule.size() > 0) {
                    FlightPlan plan = schedule.get(0);
                    if (plan.getTargetStartTime() == Time.getInstance().getUnit()) {
                        for (FlightSegment segment : plan.getFlightPath()) {
                            Waypoint origin = segment.getSegment().getFrom();
                            Waypoint destination = segment.getSegment().getTo();
                            double xDirection = destination.getX() - origin.getX();
                            double yDirection = destination.getY() - origin.getY();
                            double zDirection = destination.getZ() - origin.getZ();
                            double prevX = origin.getX();
                            double prevY = origin.getY();
                            double prevZ = origin.getZ();
                            while (operation.getCurrentX() != destination.getX() || operation.getCurrentY() != destination.getY() || operation.getCurrentZ() != destination.getZ()) {
                                operation.setCurrentX(operation.getCurrentX() + (0.1) * xDirection);
                                operation.setCurrentY(operation.getCurrentY() + (0.1) * yDirection);
                                operation.setCurrentZ(operation.getCurrentZ() + (0.1) * zDirection);
                                if (operation.getCurrentX() != prevX || operation.getCurrentY() != prevY || operation.getCurrentZ() != prevZ) {
                                    System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
                                    prevX = operation.getCurrentX();
                                    prevY = operation.getCurrentY();
                                    prevZ = operation.getCurrentZ();
                                }
                                if (xDirection > 0) {
                                    if (operation.getCurrentX() >= destination.getX())
                                        break;
                                }
                                else if (operation.getCurrentX() < destination.getX())
                                    break;

//                                break;
                            }
                        }
                    }
                    schedule.remove(0);
                }
//                if (Time.getInstance().getUnit() != prev) {
//                    System.out.println("UAV " + this.getUAVInfo().getId() + ", Time: " + Time.getInstance().getUnit());
//                }
//                prev = Time.getInstance().getUnit();
            }
        }
    }

    public void stopWork() {
        stopWork = true;
    }

    protected void done() {
        done = true;
    }

    public UAVOperation getOperation() {
        return operation;
    }
}
