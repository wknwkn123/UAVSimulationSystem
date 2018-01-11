package simulationengine;

public class SimulationConfiguration {
    private static SimulationConfiguration ourInstance = new SimulationConfiguration();
    private double speed;
    private String airspaceType = "RANDOM";
    private String flightScheduleType = "RANDOM";
    private int numberOfUAVs;

    public static SimulationConfiguration getInstance() {
        return ourInstance;
    }

    private SimulationConfiguration() {
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
}
