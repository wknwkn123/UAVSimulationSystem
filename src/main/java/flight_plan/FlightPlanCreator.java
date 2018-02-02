package flight_plan;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.Flight;
import collisionavoidanceengine.flightplan.FlightSchedule;

import java.util.List;

public interface FlightPlanCreator {
    List<Flight> createFlightPlans(AirspaceStructure airMap);
}
