package flightEngine.json_formatting;

public class Simulation {
    private String simulationStart;
    private SimulationParameter simulationParameter;

    public String getSimulationStart() {
        return simulationStart;
    }

    public void setSimulationStart(String simulationStart) {
        this.simulationStart = simulationStart;
    }

    public SimulationParameter getParameter() {
        return simulationParameter;
    }

    public void setParameter(SimulationParameter parameter) {
        this.simulationParameter = parameter;
    }
}
