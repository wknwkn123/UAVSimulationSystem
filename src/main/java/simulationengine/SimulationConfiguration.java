package simulationengine;

import config.Config;
import simulationengine.json_formatting.Simulation;

/**
 * Configuration to hold all the parameters set by front end. The one source of truth.
 */
public class SimulationConfiguration {
    private double coordinateDifferenceAllowed = 5;
    private double averageSpeed;
    private String airspaceType;
    private String flightScheduleType;
    private int numberOfUAVs = 20;
    private int numberOfFlights = 20;
    private int simulationRate;

    public SimulationConfiguration(Simulation param) {
        this.airspaceType = param.getParameter().getAirspaceType();
        this.flightScheduleType = param.getParameter().getFlightScheduleType();
        this.simulationRate = param.getParameter().getSimulationRate();
        this.averageSpeed = param.getParameter().getAverageSpeed();
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
