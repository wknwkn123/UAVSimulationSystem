package airspaceEngine.waypoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziji Shi on 20/12/17.
 */
public class WPList {
    private List<Waypoint> WaypointList = new ArrayList<Waypoint>();

    public List<Waypoint> getWaypointList() {
        return WaypointList;
    }

    public void addWaypoint(Waypoint wp){
        this.WaypointList.add(wp);
    }

    public Waypoint getByIndex (int i){return WaypointList.get(i);}

    public int getSize() {
        return WaypointList.size();
    }

    public void setWaypointList(List<Waypoint> waypointList) {
        WaypointList = waypointList;
    }

    public Waypoint getWaypointByID (String targetID){
        for (int i =0; i < WaypointList.size(); i++){
            if (WaypointList.get(i).getNodeID().equals(targetID))
                return WaypointList.get(i);
        }
        System.out.print("ERROR : No waypoint with ID "+targetID+" found!");
        return null;
    }
}