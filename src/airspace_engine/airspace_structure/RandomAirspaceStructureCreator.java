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
        for (int i = 0; i < 6; i++) {
            nodes.add(new Waypoint(i, i, 1));
            System.out.println("Waypoint " + nodes.get(i).getId() + " initialized");
        }

        //create edges
        List<RouteSegment> routeSegments = new ArrayList<>();
        routeSegments.add(new RouteSegment(nodes.get(0), nodes.get(1), 15));
        routeSegments.add(new RouteSegment(nodes.get(1), nodes.get(2), 12));
        routeSegments.add(new RouteSegment(nodes.get(0), nodes.get(3), 10));
        routeSegments.add(new RouteSegment(nodes.get(2), nodes.get(3), 5));
        routeSegments.add(new RouteSegment(nodes.get(4), nodes.get(5), 7));
        routeSegments.add(new RouteSegment(nodes.get(2), nodes.get(5), 9));

        for (int i = 0; i < routeSegments.size(); i++) {
            System.out.println("Route Segment " + routeSegments.get(i).getId() + " from " + routeSegments.get(i).getTo().getX() + " to " + routeSegments.get(i).getFrom().getX() + " initialized with weight " + routeSegments.get(i).getWeight());
        }

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(nodes, routeSegments);
        System.out.println("Airmap created.");
        return airMap;
    }

}
