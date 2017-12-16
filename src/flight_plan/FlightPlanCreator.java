package flight_plan;

import airspace_engine.airspace_structure.AirspaceStructure;

import java.util.List;

public interface FlightPlanCreator {
    List<FlightPlan> createFlightPlans(AirspaceStructure airMap);
}
