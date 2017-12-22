package collisionavoidanceengine.flightplan;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziji Shi on 22/12/17.
 */
public class EdgeRecord {
    private String edgeID;
    private double passingTime;     // time needed for a UAV to pass that edge
    public List<Map.Entry<Integer, String>> historyList = new ArrayList<>();

    public EdgeRecord(String edgeID, double passingTime) {
        // Copy the information from the graph
        this.edgeID = edgeID;
        this.passingTime = passingTime;
    }

    public void addRecord(int reachTime, String flightID){
        Map.Entry<Integer, String > newRecord = new AbstractMap.SimpleEntry<Integer, String>(reachTime,flightID);
        historyList.add(newRecord);
    }

}
