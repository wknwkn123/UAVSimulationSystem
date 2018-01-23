package airspaceengine.airspacestructure;
import airspaceengine.routesegment.RSList;
import airspaceengine.routesegment.RSMap;
import airspaceengine.waypoint.WPList;
import airspaceengine.waypoint.WPMap;
import airspaceengine.waypoint.Waypoint;
import airspaceengine.routesegment.RouteSegment;
import java.util.ArrayList;
import java.util.List;


class RandomAirspaceStructureCreator implements AirspaceStructureCreator {
    private WPMap nodes = new WPMap();
    private RSMap routeSegments = new RSMap();

    public AirspaceStructure createAirspaceStructure(){
        //create nodes
        nodes.addWaypoint(new Waypoint( "WP_00001", true,100,  100, 10));
        nodes.addWaypoint(new Waypoint("WP_00002", true, 240,  400, 10));
        nodes.addWaypoint(new Waypoint("WP_00003", true,300, 300, 10));
        nodes.addWaypoint(new Waypoint("WP_00004", true,450, 200, 10));
        nodes.addWaypoint(new Waypoint("WP_00005", true,500, 500, 10));
        nodes.addWaypoint(new Waypoint("WP_00006", true,550, 250, 10));

        //create edges
        routeSegments.addRouteSegment(new RouteSegment("RS_00001", nodes.getWaypointByID("WP_00001"), nodes.getWaypointByID("WP_00002"), 15));
        routeSegments.addRouteSegment(new RouteSegment("RS_00002", nodes.getWaypointByID("WP_00002"), nodes.getWaypointByID("WP_00003"), 12));
        routeSegments.addRouteSegment(new RouteSegment("RS_00003", nodes.getWaypointByID("WP_00001"), nodes.getWaypointByID("WP_00004"), 10));
        routeSegments.addRouteSegment(new RouteSegment("RS_00004", nodes.getWaypointByID("WP_00003"), nodes.getWaypointByID("WP_00004"), 5));
        routeSegments.addRouteSegment(new RouteSegment("RS_00005", nodes.getWaypointByID("WP_00005"), nodes.getWaypointByID("WP_00006"), 7));
        routeSegments.addRouteSegment(new RouteSegment("RS_00006", nodes.getWaypointByID("WP_00003"), nodes.getWaypointByID("WP_00006"), 9));
        routeSegments.addRouteSegment(new RouteSegment("RS_00007", nodes.getWaypointByID("WP_00002"), nodes.getWaypointByID("WP_00001"), 15));
        routeSegments.addRouteSegment(new RouteSegment("RS_00008", nodes.getWaypointByID("WP_00003"), nodes.getWaypointByID("WP_00002"), 12));
        routeSegments.addRouteSegment(new RouteSegment("RS_00009", nodes.getWaypointByID("WP_00004"), nodes.getWaypointByID("WP_00001"), 10));
        routeSegments.addRouteSegment(new RouteSegment("RS_00010", nodes.getWaypointByID("WP_00004"), nodes.getWaypointByID("WP_00003"), 5));
        routeSegments.addRouteSegment(new RouteSegment("RS_00011", nodes.getWaypointByID("WP_00006"), nodes.getWaypointByID("WP_00005"), 7));
        routeSegments.addRouteSegment(new RouteSegment("RS_00012", nodes.getWaypointByID("WP_00006"), nodes.getWaypointByID("WP_00003"), 9));

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(nodes, routeSegments);
        System.out.println("Airmap created.");
        return airMap;
    }

}
