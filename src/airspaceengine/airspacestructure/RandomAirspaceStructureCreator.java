package airspaceengine.airspacestructure;
import airspaceengine.waypoint.Waypoint;
import airspaceengine.routesegment.RouteSegment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


class RandomAirspaceStructureCreator implements AirspaceStructureCreator{

    public AirspaceStructure createAirspaceStructure(){
        //create nodes
        List<Waypoint> nodes = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            nodes.add(new Waypoint(nodeID, isTransferable, i, i, 1, adjacientNodes));
            System.out.println("Waypoint " + nodes.get(i).getX() + " initialized");
        }

        //create edges
        List<RouteSegment> routeSegments = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {
                if (i != j) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, 25);
                    routeSegments.add(new RouteSegment(edgeID, nodes.get(i), nodes.get(j), randomNum));
                    routeSegments.add(new RouteSegment(edgeID, nodes.get(j), nodes.get(i), randomNum));
                }
            }
        }
        for (int i = 0; i < routeSegments.size(); i++) {
            System.out.println(routeSegments.get(i) + " Route Segment " + routeSegments.get(i).getTo().getX() + " to " + routeSegments.get(i).getFrom().getX() + " initialized with weight " + routeSegments.get(i).getWeight());
        }

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(nodes, routeSegments);
        System.out.println("Airmap created.");
        return airMap;
    }

}
