package simulationengine;

import config.Config;

/**
 * Configuration to hold all the parameters set by front end. The one source of truth.
 */
public class SimulationConfiguration {
    private static SimulationConfiguration ourInstance = new SimulationConfiguration();
    private double coordinateDifferenceAllowed = 5;
    private double speed = 0.05;
    private String airspaceType = "PLANARGRAPH";
    private String flightScheduleType = "RANDOM";
    private int numberOfUAVs;
    private Config config = new Config();

    public static SimulationConfiguration getInstance() {
        return ourInstance;
    }

    private SimulationConfiguration() {
    }

    public double getCoordinateDifferenceAllowed() {
        return coordinateDifferenceAllowed;
    }

    public void setCoordinateDifferenceAllowed(double coordinateDifferenceAllowed) {
        this.coordinateDifferenceAllowed = coordinateDifferenceAllowed;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
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

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
