package airspace_engine.airspace_structure;
import airspace_engine.waypoint.Waypoint;
import airspace_engine.route_segment.RouteSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


class RandomAirspaceStructureCreator implements AirspaceStructureCreator{

    public AirspaceStructure createAirspaceStructure(){
        //create nodes
        List<Waypoint> nodes = new ArrayList<>();
        nodes.add(new Waypoint( 100,  100, 1));
        nodes.add(new Waypoint(240,  400, 1));
        nodes.add(new Waypoint(300, 300, 1));
        nodes.add(new Waypoint(450, 200, 1));
        nodes.add(new Waypoint(500, 500, 1));
        nodes.add(new Waypoint(550, 250, 1));

        for (Waypoint node : nodes) {
            System.out.println("Waypoint " + node.getId() + " initialized");
        }

        //create edges
        List<RouteSegment> routeSegments = new ArrayList<>();
        routeSegments.add(new RouteSegment(nodes.get(0), nodes.get(1), 15));
        routeSegments.add(new RouteSegment(nodes.get(1), nodes.get(2), 12));
        routeSegments.add(new RouteSegment(nodes.get(0), nodes.get(3), 10));
        routeSegments.add(new RouteSegment(nodes.get(2), nodes.get(3), 5));
        routeSegments.add(new RouteSegment(nodes.get(4), nodes.get(5), 7));
        routeSegments.add(new RouteSegment(nodes.get(2), nodes.get(5), 9));
        routeSegments.add(new RouteSegment(nodes.get(1), nodes.get(0), 15));
        routeSegments.add(new RouteSegment(nodes.get(2), nodes.get(1), 12));
        routeSegments.add(new RouteSegment(nodes.get(3), nodes.get(0), 10));
        routeSegments.add(new RouteSegment(nodes.get(3), nodes.get(2), 5));
        routeSegments.add(new RouteSegment(nodes.get(5), nodes.get(4), 7));
        routeSegments.add(new RouteSegment(nodes.get(5), nodes.get(2), 9));

        for (int i = 0; i < routeSegments.size(); i++) {
            System.out.println("Route Segment " + routeSegments.get(i).getId() + " from " + routeSegments.get(i).getTo().getX() + " to " + routeSegments.get(i).getFrom().getX() + " initialized with weight " + routeSegments.get(i).getWeight());
        }

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(nodes, routeSegments);
        System.out.println("Airmap created.");
        return airMap;
    }

}
