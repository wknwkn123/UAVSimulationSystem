package collisionavoidanceengine.flightplan;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static collisionavoidanceengine.constants.Constant.WAITING_PENALTY_AT_NON_LANDING_NODE;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class EdgeRecord {
    private String edgeID;
    private double passingTime;     // time needed for a UAV to pass that edge
    public List<Map.Entry<String,Double>> historyList = new ArrayList<>();      // in form of <flightID, reachTime>

    public EdgeRecord(String edgeID, double passingTime) {
        // Copy the information from the graph
        this.edgeID = edgeID;
        this.passingTime = passingTime;
    }

    public void addRecord(String flightID, double reachTime){
        // leaveTime  = reachTime + passingTime. To reduce data redundancy, we ommit the leaveTime field.
        Map.Entry<String, Double> newRecord = new AbstractMap.SimpleEntry<String, Double>(flightID, reachTime);
        historyList.add(newRecord);
    }

    public List<Map.Entry<String, Double>> getHistoryList() {
        return historyList;
    }

    public double getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(double passingTime) {
        this.passingTime = passingTime;
    }

    public String getEdgeID() {
        return edgeID;
    }

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    // Loop through the list of meta-record to see whether a given time falls in an existing schedule,
    public double getWaitingPenalty(double newArrival){
        // Reverse iteration so that some search time may be saved
        for (int i=historyList.size()-1;i>=0;i--){
            double existingScheduledArrival = historyList.get(i).getValue();

            // When two UAV arrives at the same time, we let the previously scheduled UAV fly first
            if((existingScheduledArrival-newArrival)<WAITING_PENALTY_AT_NON_LANDING_NODE)
                // the latter will have to wait for the whole period
                return passingTime;
            // If new UAV arrives while another one is already occupying that edge
            else if(existingScheduledArrival<newArrival && newArrival<historyList.get(i).getValue()+passingTime)
                // the latter will have to wait for it to complete
                return (historyList.get(i).getValue()+passingTime-newArrival);
        }
        // No waiting penalty; this edge is free to use.
        return 0.0;
    }
}
