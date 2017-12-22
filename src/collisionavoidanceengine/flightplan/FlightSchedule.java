package collisionavoidanceengine.flightplan;

import java.util.List;
import java.util.Map;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class FlightSchedule {
    private List<EdgeRecord> edgeAvailability;
    private List<NodeRecord> nodeAvailability;
    private List<Flight> flightPlan;

    // To ensure thread-safety. Not yet used.
    private boolean mutex;






}
