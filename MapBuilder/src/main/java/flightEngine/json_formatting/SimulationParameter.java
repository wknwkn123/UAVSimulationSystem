package flightEngine.json_formatting;

public class SimulationParameter {
    private String airspaceType;
    private String flightScheduleType;
    private double averageSpeed;
    private int simulationRate;

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

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getSimulationRate() {
        return simulationRate;
    }

    public void setSimulationRate(int simulationRate) {
        this.simulationRate = simulationRate;
    }
}

