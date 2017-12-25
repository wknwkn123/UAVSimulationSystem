package collisionavoidanceengine.flightplan;

import java.util.ArrayList;
import java.util.List;

import static collisionavoidanceengine.constants.Constant.WAITING_PENALTY_AT_LANDING_NODE;
import static collisionavoidanceengine.constants.Constant.WAITING_PENALTY_AT_NON_LANDING_NODE;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class NodeRecord {
    private String nodeID;
    private boolean isTransferable;
    public List<MetaNodeRecord> historyList = new ArrayList<>();

    public NodeRecord(String nodeID, boolean isTransferable) {
        this.nodeID = nodeID;
        this.isTransferable = isTransferable;
    }

    public void addRecord(int reachTime, String flightID, boolean isLanding){
        MetaNodeRecord newRecord = new MetaNodeRecord(reachTime,flightID, isLanding);
        historyList.add(newRecord);
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }



    public boolean isTransferable() {
        return isTransferable;
    }

    public void setTransferable(boolean transferable) {
        isTransferable = transferable;
    }

    public double getWaitingPenalty (int newArrival){
        for (int i=0; i<historyList.size();i++){
            MetaNodeRecord currentSchedule = historyList.get(i);

            // When two UAV arrives at the same node at same time, we let the previously scheduled UAV fly first
            if(currentSchedule.reachTime==newArrival)
                // If the former UAV is attempting to land
                if (this.isTransferable && currentSchedule.isLanding)
                    return WAITING_PENALTY_AT_LANDING_NODE;
                else
                    return WAITING_PENALTY_AT_NON_LANDING_NODE;
        }
        // No waiting penalty; this node is free to use.
        return 0.0;
    }
}

