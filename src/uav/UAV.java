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
    //constructor
    public UAV(String uav_name, String uav_owner, String uav_manufacturer, String model_type, double max_speed, double max_altitude, Date last_certification_date) {
        UAVInfo = new UAVInfo(uav_name, uav_owner, uav_manufacturer, model_type, max_speed, max_altitude, last_certification_date);
        schedule = new ArrayList<>();
        operation = new UAVOperation();
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

    public void addJob(FlightPlan job) {
        schedule.add(job);
    }

    public void run() {
            for (FlightPlan plan : schedule) {
                for (FlightSegment segment : plan.getFlightPath()) {
                    Waypoint origin = segment.getSegment().getFrom();
                    Waypoint destination = segment.getSegment().getTo();
                    double x = destination.getX() - origin.getX();
                    double y = destination.getY() - origin.getY();
                    double z = destination.getZ() - origin.getZ();
                    while (operation.getCurrentX() != destination.getX() && operation.getCurrentY() != destination.getY() && operation.getCurrentZ() != destination.getZ()) {
                        operation.setCurrentX(operation.getCurrentX() + (1 / segment.getSegment().getWeight()) * x);
                        operation.setCurrentY(operation.getCurrentZ() + (1 / segment.getSegment().getWeight()) * y);
                        operation.setCurrentZ(operation.getCurrentZ() + (1 / segment.getSegment().getWeight()) * z);
                    }
                }
            }
    }
}
