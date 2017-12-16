package airspace_engine.airspace_structure;
import airspace_engine.route_segment.RouteSegment;
import airspace_engine.waypoint.Waypoint;

import java.util.List;

public class AirspaceStructure {
    private final List<Waypoint> nodes;
    private final List<RouteSegment> edges;

    public AirspaceStructure(List<Waypoint> nodes, List<RouteSegment> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Waypoint> getNodes(){
        return nodes;
    }

    public List<RouteSegment> getEdges() {
        return edges;
    }
}
