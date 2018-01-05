package uav;
import airspaceengine.waypoint.Waypoint;
import flight_plan.FlightPlan;
import flight_plan.FlightSegment;
import simulationengine.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UAV implements Runnable{
    private UAVInfo UAVInfo;
    private UAVOperation operation;
    private List<FlightPlan> schedule;
    private volatile boolean stopWork;
    private UAVJSON jsonData;
    private double speed;
    private boolean done;

    //constructor
    public UAV(String uav_manufacturer, String model_type, double max_speed, double max_altitude) {
        UAVInfo = new UAVInfo(uav_manufacturer, model_type, max_speed, max_altitude);
        schedule = new ArrayList<>();
        operation = new UAVOperation();
        jsonData = new UAVJSON();
        jsonData.setId(this.getUAVInfo().getId());
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

    public UAVJSON getJsonData() { return jsonData; }

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
                    if (plan.getTargetStartTime() > Time.getInstance().getUnit()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(300 * (plan.getTargetStartTime() - Time.getInstance().getUnit()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (plan.getTargetStartTime() <= Time.getInstance().getUnit()) {
                        System.out.println("Flight plan " + plan.getId() + " started");
                        for (FlightSegment segment : plan.getFlightPath()) {
                            Waypoint origin = segment.getSegment().getFrom();
                            Waypoint destination = segment.getSegment().getTo();
                            double xDirection = destination.getX() - origin.getX();
                            double yDirection = destination.getY() - origin.getY();
                            double zDirection = destination.getZ() - origin.getZ();
                            double prevX = origin.getX();
                            double prevY = origin.getY();
                            double prevZ = origin.getZ();

                            while (Math.abs(operation.getCurrentX() - destination.getX()) > 1 || Math.abs(operation.getCurrentY() - destination.getY()) > 1 || Math.abs(operation.getCurrentZ() - destination.getZ()) > 1) {
                                if (Math.abs(operation.getCurrentX() - destination.getX()) > 0) {
                                    operation.setCurrentX(operation.getCurrentX() + (0.05) * xDirection);
                                }

                                if (Math.abs(operation.getCurrentY() - destination.getY()) > 0) {
                                    operation.setCurrentY(operation.getCurrentY() + (0.05) * yDirection);
                                }

                                if (Math.abs(operation.getCurrentZ() - destination.getZ()) > 0) {
                                    operation.setCurrentZ(operation.getCurrentZ() + (0.05) * zDirection);
                                }

                                if (operation.getCurrentX() != prevX || operation.getCurrentY() != prevY || operation.getCurrentZ() != prevZ) {
                                    setJSONData();
                                    System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
                                    prevX = operation.getCurrentX();
                                    prevY = operation.getCurrentY();
                                    prevZ = operation.getCurrentZ();
                                }

                                try {
                                    TimeUnit.MILLISECONDS.sleep(250);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
                        System.out.println("Flight plan " + plan.getId() + " completed.");
                        System.out.println();
                        schedule.remove(0);
                    }
                }
            }
        }
    }

    public void setJSONData() {
        jsonData.setTime(Time.getInstance().getRealTime());
        jsonData.setCoordinate(new Coordinate(operation.getCurrentX(), operation.getCurrentZ(), operation.getCurrentY()));
        jsonData.setPlanID(schedule.get(0).getId());
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
