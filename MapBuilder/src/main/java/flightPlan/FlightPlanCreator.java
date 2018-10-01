package flightPlan;

import airspaceEngine.airspacestructure.AirspaceStructure;
import flightEngine.FlightConfiguration;

import java.util.List;

public interface FlightPlanCreator {
    List<Flight> createFlightPlans(AirspaceStructure airMap, FlightConfiguration config);
}
