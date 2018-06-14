package collisionavoidanceengine.runtime;

import collisionavoidanceengine.flightplan.MetaNodeRecord;

import java.util.List;

/**
 * Created by Ziji Shi on 2/1/18.
 *
 * Temporarily store the routing results.
 * Will need to be converted to RoutingResult if the routing time is accepted.
 */
public class TemporaryRoutingResult {
    public double RequestID;
    public double flightTime;
    public List<MetaNodeRecord> flightPath;
}
