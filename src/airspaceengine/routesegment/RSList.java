package airspaceengine.routesegment;

import airspaceengine.waypoint.Waypoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ziji Shi on 20/12/17.
 */
public class RSList {
    private List<RouteSegment> RouteSegList = new ArrayList<RouteSegment>();

    public List<RouteSegment> getRouteSegList() {
        return RouteSegList;
    }

    public void addRouteSegment(RouteSegment rs){
        this.RouteSegList.add(rs);
    }

    public RouteSegment getRouteSegmentByID(String targetID){
        for (int i =0; i < RouteSegList.size(); i++){
            if (RouteSegList.get(i).getEdgeID().equals(targetID))
                return RouteSegList.get(i);
        }
        System.out.print("ERROR : No route segment with ID "+targetID+" found!");
        return null;
    }

}
