package collision_avoidance_engine.assets;

import java.util.List;
/**
 * Created by Ziji Shi on 19/12/17.
 *
 * Graph consists of edge (RouteSegment) and node (Waypoint).
 *
 * For here, we use demo.json as input graph file, but we will give a unique ID for
 * each node and edge.
 */
public class Graph {
    private final List<Node> nodes;
    private final List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }
}
