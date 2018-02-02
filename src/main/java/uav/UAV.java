package uav;
import airspaceengine.AirspaceEngine;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.flightplan.Flight;
import com.google.gson.Gson;
import flight_plan.FlightPlan;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import simulationengine.SimulationConfiguration;
import simulationengine.Time;
import uav.uav_json_encoder.NodePoint;
import uav.uav_json_encoder.UAVJSON;
import websocket.simple_v2.server.Websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * The UAV class is a thread. It updates itself at intervals and send it to the simulation engine.
 */
public class UAV implements Runnable{
    private UAVInfo UAVInfo;
    private UAVOperation operation;
    private List<FlightPlan> schedule;
    private List<Flight> flightPlans;
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
        flightPlans = new ArrayList<>();
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

    public void addFlightPlan(Flight job) {
        flightPlans.add(job);
    }

    public void setOrigin() {
        if (flightPlans.size() > 0) {
            operation.setCurrentX(AirspaceEngine.getInstance().getAirMap().getWPByID(flightPlans.get(0).getFlightPath().get(0)).getX());
            operation.setCurrentY(AirspaceEngine.getInstance().getAirMap().getWPByID(flightPlans.get(0).getFlightPath().get(0)).getY());
            operation.setCurrentZ(AirspaceEngine.getInstance().getAirMap().getWPByID(flightPlans.get(0).getFlightPath().get(0)).getZ());
        }
    }

    public void run() {
        while(!stopWork && !done) {
            while(!Time.getInstance().isCompleted()) {
                if (flightPlans.size() > 0) {
                    Flight plan = flightPlans.get(0);
                    if (plan.getDepartTime() > Time.getInstance().getUnit()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000 * (plan.getDepartTime() - Time.getInstance().getUnit()));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (plan.getDepartTime() <= Time.getInstance().getUnit()) {
                        System.out.println("Flight plan " + plan.getFlightID() + " started");
                        for (int i = 0; i < plan.getFlightPath().size() - 1; i++) {
                            Waypoint origin = AirspaceEngine.getInstance().getAirMap().getWPByID(plan.getFlightPath().get(i));
                            Waypoint destination = AirspaceEngine.getInstance().getAirMap().getWPByID(plan.getFlightPath().get(i+1));
                            double xDirection = destination.getX() - origin.getX();
                            double yDirection = destination.getY() - origin.getY();
                            double zDirection = destination.getZ() - origin.getZ();
                            double prevX = origin.getX();
                            double prevY = origin.getY();
                            double prevZ = origin.getZ();

                            while (Math.abs(operation.getCurrentX() - destination.getX()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentY() - destination.getY()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentZ() - destination.getZ()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
                                if (Math.abs(operation.getCurrentX() - destination.getX()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
                                    operation.setCurrentX(operation.getCurrentX() + SimulationConfiguration.getInstance().getSpeed() * xDirection);
                                }

                                if (Math.abs(operation.getCurrentY() - destination.getY()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
                                    operation.setCurrentY(operation.getCurrentY() + SimulationConfiguration.getInstance().getSpeed() * yDirection);
                                }

                                if (Math.abs(operation.getCurrentZ() - destination.getZ()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
                                    operation.setCurrentZ(operation.getCurrentZ() + SimulationConfiguration.getInstance().getSpeed() * zDirection);
                                }

                                if (Math.abs(operation.getCurrentX() - prevX) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentY() - prevY) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentZ() - prevZ) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
                                    setJSONData(origin, destination);
                                    System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
                                    prevX = operation.getCurrentX();
                                    prevY = operation.getCurrentY();
                                    prevZ = operation.getCurrentZ();
                                }

                                try {
                                    TimeUnit.MILLISECONDS.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                if(stopWork)
                                    break;
                            }
                            if(stopWork)
                                break;
                        }
                        System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
                        RemoteEndpoint remote = Websocket.getInstance().getSession().getRemote();
                        try {
                            Gson gson = new Gson();
                            String jsonData = gson.toJson(this.jsonData);
                            remote.sendString(jsonData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Flight plan " + plan.getFlightID() + " completed.");
                        System.out.println();
                        flightPlans.remove(0);
                    }
                }
            }
        }
    }

//    public void run() {
//        while(!stopWork && !done) {
//            while(!Time.getInstance().isCompleted()) {
//                if (schedule.size() > 0) {
//                    FlightPlan plan = schedule.get(0);
//                    if (plan.getTargetStartTime() > Time.getInstance().getUnit()) {
//                        try {
//                            TimeUnit.MILLISECONDS.sleep(300 * (plan.getTargetStartTime() - Time.getInstance().getUnit()));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    if (plan.getTargetStartTime() <= Time.getInstance().getUnit()) {
//                        System.out.println("Flight plan " + plan.getId() + " started");
//                        for (FlightSegment segment : plan.getFlightPath()) {
//                            Waypoint origin = segment.getSegment().getFrom();
//                            Waypoint destination = segment.getSegment().getTo();
//                            double xDirection = destination.getX() - origin.getX();
//                            double yDirection = destination.getY() - origin.getY();
//                            double zDirection = destination.getZ() - origin.getZ();
//                            double prevX = origin.getX();
//                            double prevY = origin.getY();
//                            double prevZ = origin.getZ();
//
//                            while (Math.abs(operation.getCurrentX() - destination.getX()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentY() - destination.getY()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentZ() - destination.getZ()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
//                                if (Math.abs(operation.getCurrentX() - destination.getX()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
//                                    operation.setCurrentX(operation.getCurrentX() + SimulationConfiguration.getInstance().getSpeed() * xDirection);
//                                }
//
//                                if (Math.abs(operation.getCurrentY() - destination.getY()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
//                                    operation.setCurrentY(operation.getCurrentY() + SimulationConfiguration.getInstance().getSpeed() * yDirection);
//                                }
//
//                                if (Math.abs(operation.getCurrentZ() - destination.getZ()) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
//                                    operation.setCurrentZ(operation.getCurrentZ() + SimulationConfiguration.getInstance().getSpeed() * zDirection);
//                                }
//
//                                if (Math.abs(operation.getCurrentX() - prevX) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentY() - prevY) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed() || Math.abs(operation.getCurrentZ() - prevZ) > SimulationConfiguration.getInstance().getCoordinateDifferenceAllowed()) {
//                                    setJSONData();
//                                    System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
//                                    prevX = operation.getCurrentX();
//                                    prevY = operation.getCurrentY();
//                                    prevZ = operation.getCurrentZ();
//                                }
//
//                                try {
//                                    TimeUnit.MILLISECONDS.sleep(250);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                        System.out.println("UAV " + this.getUAVInfo().getId() + " is now at (" + operation.getCurrentX() + ", " + operation.getCurrentY() + ", " + operation.getCurrentZ() + ")");
//                        RemoteEndpoint remote = Websocket.getInstance().getSession().getRemote();
//                        try {
//                            Gson gson = new Gson();
//                            String jsonData = gson.toJson(this.jsonData);
//                            remote.sendString(jsonData);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println("Flight plan " + plan.getId() + " completed.");
//                        System.out.println();
//                        schedule.remove(0);
//                    }
//                }
//            }
//        }
//    }

    public void setJSONData(Waypoint startPoint, Waypoint endPoint) {
        jsonData.setTime(Time.getInstance().getRealTime());
        jsonData.setCoordinate(new Coordinate(operation.getCurrentX(), operation.getCurrentY(), operation.getCurrentZ()));
//        Waypoint startPoint = schedule.get(0).getStartPoint();
        jsonData.setStartPoint(new NodePoint(startPoint.getNodeID(), new Coordinate(startPoint.getX(), startPoint.getY(), startPoint.getZ())));
//        Waypoint endPoint = schedule.get(0).getEndPoint();
        jsonData.setEndPoint(new NodePoint(endPoint.getNodeID(), new Coordinate(endPoint.getX(), endPoint.getY(), endPoint.getZ())));
        jsonData.setPlanID(flightPlans.get(0).getFlightID());
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

    public List<Flight> getFlightPlans() { return flightPlans; }
}
