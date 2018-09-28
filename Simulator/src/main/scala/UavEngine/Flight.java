package UavEngine;

import java.util.ArrayList;

/**
 *
 *  Taken From last verssion of JAVA UAV sismulation project done by NTU Students
 *
 * Created by Ziji Shi on 22/12/17.
 *
 * This class models the individual flights
 */

public class Flight {
    //private static int idCount = 1;
    private String flightID;
    private String requestID;
    private String UAVID;
    private ArrayList<Waypoint> flightPath;    // An array list of nodes on the flight path
    private int departTime;
    private int arrivalTime;


    public Flight(String flightID,String requestID, String UAVID, int departTime, int arrivalTime) {
        //this.flightID = "FL_" + String.format("%05d", idCount);
        //idCount++;
        this.flightID = flightID;
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

    public ArrayList<Waypoint> getFlightPath() {
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

    public void setFlightPath(ArrayList<Waypoint> flightPath) {
        this.flightPath = flightPath;
    }
}
