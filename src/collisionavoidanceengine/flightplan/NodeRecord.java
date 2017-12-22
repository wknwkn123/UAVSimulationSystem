package collisionavoidanceengine.flightplan;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class NodeRecord {
    private String nodeID;
    private boolean isTransferable;
    public List<MetaNodeRecord> historyList = new ArrayList<>();

    public void addRecord(int reachTime, String flightID, boolean isLanding){
        MetaNodeRecord newRecord = new MetaNodeRecord(reachTime,flightID, isLanding);
        historyList.add(newRecord);
    }
}
