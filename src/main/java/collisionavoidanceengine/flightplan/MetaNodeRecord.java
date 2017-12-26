package collisionavoidanceengine.flightplan;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class MetaNodeRecord {
    public int reachTime;
    public String flightID;
    public boolean isLanding;

    public MetaNodeRecord(int reachTime, String flightID, boolean isLanding) {
        this.reachTime=reachTime;
        this.flightID=flightID;
        this.isLanding=isLanding;
    }
}
