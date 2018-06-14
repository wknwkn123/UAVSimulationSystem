package airspaceengine.routesegment;

import airspaceengine.waypoint.Waypoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziji Shi on 20/12/17.
 */
public class RSMap {
    private Map<String, RouteSegment> RouteSegMap = new HashMap<>();

    public Map<String,RouteSegment> getRouteSegMap() {
        return RouteSegMap;
    }

    public void addRouteSegment(RouteSegment rs){
        this.RouteSegMap.put(rs.getEdgeID(),rs);
    }

    public int getSize(){
        return RouteSegMap.size();
    }
//    public RouteSegment getByIndex(int i){
//        return RouteSegList.get(i);
//    }

    public RouteSegment getRouteSegmentByID(String targetID){
        if(this.RouteSegMap.containsKey(targetID))
            return this.RouteSegMap.get(targetID);
        else
            System.out.print("ERROR : No route segment with ID "+targetID+" found!");
            return null;
    }

}
