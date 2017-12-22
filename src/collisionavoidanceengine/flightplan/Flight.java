package collisionavoidanceengine.flightplan;

import java.util.ArrayList;

/**
 * Created by Ziji Shi on 22/12/17.
 *
 * This class models the individual flights
 */
public class Flight {
    private String flightID;
    private String requestID;
    private ArrayList<String> flightPath;
    private int departTime;
    private int arrivalTime;


    public Flight(String flightID, String requestID, ArrayList<String> flightPath, int departTime, int arrivalTime) {
        this.flightID = flightID;
        this.requestID = requestID;
        this.flightPath = flightPath;
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
}
