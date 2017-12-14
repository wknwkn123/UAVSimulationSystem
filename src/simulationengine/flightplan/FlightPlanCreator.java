package simulationengine.flightplan;

import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.List;

public interface FlightPlanCreator {
    List<FlightPlan> createFlightPlans(AirspaceStructure airMap);
}
