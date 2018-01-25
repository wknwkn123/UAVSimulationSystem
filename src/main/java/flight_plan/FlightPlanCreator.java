package flight_plan;
import airspaceengine.airspacestructure.AirspaceStructure;

import java.util.List;

public interface FlightPlanCreator {
    List<FlightPlan> createFlightPlans(AirspaceStructure airMap);
}
