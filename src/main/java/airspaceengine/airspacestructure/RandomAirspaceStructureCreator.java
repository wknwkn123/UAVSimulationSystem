package airspaceengine.airspacestructure;
import airspaceengine.routesegment.RSList;
import airspaceengine.waypoint.WPList;
import airspaceengine.waypoint.Waypoint;
import airspaceengine.routesegment.RouteSegment;
import java.util.ArrayList;
import java.util.List;


class RandomAirspaceStructureCreator implements AirspaceStructureCreator {

    public AirspaceStructure createAirspaceStructure(){
        //create nodes
        List<Waypoint> nodes = new ArrayList<>();
        nodes.add(new Waypoint( "WP_00001", true,100,  100, 10));
        nodes.add(new Waypoint("WP_00002", true, 240,  400, 10));
        nodes.add(new Waypoint("WP_00003", true,300, 300, 10));
        nodes.add(new Waypoint("WP_00004", true,450, 200, 10));
        nodes.add(new Waypoint("WP_00005", true,500, 500, 10));
        nodes.add(new Waypoint("WP_00006", true,550, 250, 10));

        for (Waypoint node : nodes) {
            System.out.println("Waypoint " + node.getNodeID() + " initialized");
        }

        //create edges
        List<RouteSegment> routeSegments = new ArrayList<>();
        routeSegments.add(new RouteSegment("RS_00001", nodes.get(0), nodes.get(1), 15));
        routeSegments.add(new RouteSegment("RS_00002", nodes.get(1), nodes.get(2), 12));
        routeSegments.add(new RouteSegment("RS_00003", nodes.get(0), nodes.get(3), 10));
        routeSegments.add(new RouteSegment("RS_00004", nodes.get(2), nodes.get(3), 5));
        routeSegments.add(new RouteSegment("RS_00005", nodes.get(4), nodes.get(5), 7));
        routeSegments.add(new RouteSegment("RS_00006", nodes.get(2), nodes.get(5), 9));
        routeSegments.add(new RouteSegment("RS_00007", nodes.get(1), nodes.get(0), 15));
        routeSegments.add(new RouteSegment("RS_00008", nodes.get(2), nodes.get(1), 12));
        routeSegments.add(new RouteSegment("RS_00009", nodes.get(3), nodes.get(0), 10));
        routeSegments.add(new RouteSegment("RS_00010", nodes.get(3), nodes.get(2), 5));
        routeSegments.add(new RouteSegment("RS_00011", nodes.get(5), nodes.get(4), 7));
        routeSegments.add(new RouteSegment("RS_00012", nodes.get(5), nodes.get(2), 9));

        for (int i = 0; i < routeSegments.size(); i++) {
            System.out.println("Route Segment " + routeSegments.get(i).getEdgeID() + " from " + routeSegments.get(i).getTo().getX() + " to " + routeSegments.get(i).getFrom().getX() + " initialized with weight " + routeSegments.get(i).getWeight());
        }

        //create airspace
        WPList wpList = new WPList();
        wpList.setWaypointList(nodes);
        RSList rsList = new RSList();
        rsList.setRouteSegList(routeSegments);
        AirspaceStructure airMap = new AirspaceStructure(wpList, rsList);
        System.out.println("Airmap created.");
        return airMap;
    }

}
