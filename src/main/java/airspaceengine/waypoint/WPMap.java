package airspaceengine.waypoint;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ziji Shi on 20/12/17.
 */
public class WPMap {
    // Use map instead of list to facilitate the search time
    private Map<String, Waypoint> WaypointMap = new HashMap<String, Waypoint>();

    public Map<String,Waypoint> getWaypointMap() {
        return WaypointMap;
    }

    // Return a list of WayPoints (order is not guaranteed)
    public Waypoint[] getWaypointList(){
        return WaypointMap.values().toArray(new Waypoint[0]);
    }

    public void addWaypoint(Waypoint wp){
        this.WaypointMap.put(wp.getNodeID(),wp);
    }

//    public Waypoint getByIndex (int i){return WaypointList.get(i);}

    public int getSize() {
        return WaypointMap.size();
    }

    public Waypoint getWaypointByID (String targetID){
        for (int i =0; i < WaypointMap.size(); i++){
            if (WaypointMap.get(i).getNodeID().equals(targetID))
                return WaypointMap.get(i);
        }
        System.out.print("ERROR : No waypoint with ID "+targetID+" found!");
        return null;
    }
}
