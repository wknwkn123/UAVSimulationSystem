package collisionavoidanceengine.flightplan;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class MetaNodeRecord {
    public double reachTime;
    public String flightID;
    public boolean isLanding;
    public boolean isTakingOff;

    public MetaNodeRecord(double reachTime, String flightID, boolean isLanding, boolean isTakingOff) {
        this.reachTime=reachTime;
        this.flightID=flightID;
        this.isLanding=isLanding;
        this.isTakingOff=isTakingOff;
    }
}
