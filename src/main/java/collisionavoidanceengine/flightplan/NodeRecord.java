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

    public double getWaitingPenalty (double newArrival){
        for (int i=historyList.size()-1; i>=0;i--){
            MetaNodeRecord currentSchedule = historyList.get(i);

            // If this is a transferable node
            if (this.isTransferable){
                if (currentSchedule.isLanding){
                    // When current UAV start landing
                    double lowBound = currentSchedule.reachTime;
                    // When current UAV finishes landing
                    double uppBound  = lowBound+WAITING_PENALTY_AT_LANDING_NODE;
                    // If the new UAV is passing a node while current UAV is landing
                    if (lowBound<=newArrival && newArrival<=uppBound)
                        return uppBound-newArrival;
                }
                // If none of the UAVs is landing, but they arrives at the same time, we let the previously scheduled UAV fly first
                if (currentSchedule.reachTime == newArrival)
                    return WAITING_PENALTY_AT_NON_LANDING_NODE;
            }
            // If this is a non-transferable node, but they arrive at the same time
            else if (currentSchedule.reachTime == newArrival)
                return WAITING_PENALTY_AT_NON_LANDING_NODE;
        }
        // No waiting penalty; this node is free to use.
        return 0.0;
    }
}

