package flight_plan;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.FlightSchedule;

import java.util.List;

public interface FlightPlanCreator {
    FlightSchedule createFlightPlans(AirspaceStructure airMap);
}
