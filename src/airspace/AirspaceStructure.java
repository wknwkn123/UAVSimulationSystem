package airspace;
import java.util.List;

public class AirspaceStructure {
    private final List<Waypoint> nodes;
    private final List<RouteSegment> edges;
    
    public AirspaceStructure(List<Waypoint> nodes, List<RouteSegment> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    //class method
    public void addNode(Waypoint new_node) {
        nodes.add(new_node);
    }

    public void addEdge(RouteSegment new_edge) {
        edges.add(new_edge);
    }

//	public void removeNode(EnroutePoint node) {
//		List<EnroutePoint> deleteCandidates = new ArrayList<EnroutePoint>();
//		deleteCandidates.add(node);
//		for (EnroutePoint deleteCandidate : deleteCandidates) {
//			edges.remove(deleteCandidate);
//		 }
//	}

    public List<Waypoint> getNodes(){
        return nodes;
    }

    public List<RouteSegment> getEdges() {
        return edges;
    }
}
