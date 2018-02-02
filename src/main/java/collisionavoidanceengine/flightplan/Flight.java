package collisionavoidanceengine.flightplan;

import java.util.ArrayList;

/**
 * Created by Ziji Shi on 22/12/17.
 *
 * This class models the individual flights
 */
public class Flight {
    private static int idCount = 1;
    private String flightID;
    private String requestID;
    private String UAVID;
    private ArrayList<String> flightPath;    // An array list of nodes on the flight path
    private int departTime;
    private int arrivalTime;


    public Flight(String requestID, String UAVID, int departTime, int arrivalTime) {
        this.flightID = "FL_" + String.format("%05d", idCount);
        idCount++;
        this.requestID = requestID;
        this.UAVID = UAVID;
        this.departTime = departTime;
        this.arrivalTime = arrivalTime;
    }

    public String getFlightID() {
        return flightID;
    }

    public String getRequestID() {
        return requestID;
    }

    public ArrayList<String> getFlightPath() {
        return flightPath;
    }

    public int getDepartTime() {
        return departTime;
    }

    public void setDepartTime(int departTime) {
        this.departTime = departTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getUAVID() {
        return UAVID;
    }

    public void setUAVID(String UAVID) {
        this.UAVID = UAVID;
    }

    public void setFlightPath(ArrayList<String> flightPath) {
        this.flightPath = flightPath;
    }
}
