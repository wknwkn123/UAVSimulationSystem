package flightEngine;

import flightEngine.json_formatting.Simulation;

/**
 * Configuration to hold all the parameters set by front end. The one source of truth.
 */
public class FlightConfiguration {
    private double coordinateDifferenceAllowed = 5;
    private double averageSpeed;
    private String airspaceType;
    private String flightScheduleType;
    private int numberOfUAVs = 0;
    private int numberOfFlights = 0;
    private int simulationRate;

    public FlightConfiguration(Simulation param) {
        this.airspaceType = param.getParameter().getAirspaceType();
        this.flightScheduleType = param.getParameter().getFlightScheduleType();
        this.simulationRate = param.getParameter().getSimulationRate();
        this.averageSpeed = param.getParameter().getAverageSpeed();
        this.numberOfFlights = param.getParameter().getUAV();
    }

    public double getCoordinateDifferenceAllowed() {
        return coordinateDifferenceAllowed;
    }

    public void setCoordinateDifferenceAllowed(double coordinateDifferenceAllowed) {
        this.coordinateDifferenceAllowed = coordinateDifferenceAllowed;
    }

    public double getSpeed() {
        return averageSpeed;
    }

    public void setSpeed(double speed) {
        this.averageSpeed = speed;
    }

    public String getAirspaceType() {
        return airspaceType;
    }

    public void setAirspaceType(String airspaceType) {
        this.airspaceType = airspaceType;
    }

    public String getFlightScheduleType() {
        return flightScheduleType;
    }

    public void setFlightScheduleType(String flightScheduleType) {
        this.flightScheduleType = flightScheduleType;
    }

    public int getNumberOfUAVs() {
        return numberOfUAVs;
    }

    public void setNumberOfUAVs(int numberOfUAVs) {
        this.numberOfUAVs = numberOfUAVs;
    }

    public int getNumberOfFlights() {
        return numberOfFlights;
    }

    public void setNumberOfFlights(int numberOfFlights) {
        this.numberOfFlights = numberOfFlights;
    }
}
