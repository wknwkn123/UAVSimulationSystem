package airspaceengine.airspacestructure;
import airspaceengine.routesegment.RSList;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.WPList;
import airspaceengine.waypoint.Waypoint;

public class AirspaceStructure {
    private final WPList nodes;
    private final RSList edges;

    AirspaceStructure(WPList nodes, RSList edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // Use the method implemented in RSList and WPList
    public void addNode(Waypoint new_node) {
        nodes.addWaypoint(new_node);
    }

    public Waypoint getWPByID(String targetID){
        return this.nodes.getWaypointByID(targetID);
    }

    public RouteSegment getRSByID(String targetID){
        return this.edges.getRouteSegmentByID(targetID);
    }

    public void addEdge(RouteSegment new_edge) {
        edges.addRouteSegment(new_edge);
    }

//	public void removeNode(EnroutePoint node) {
//		List<EnroutePoint> deleteCandidates = new ArrayList<EnroutePoint>();
//		deleteCandidates.add(node);
//		for (EnroutePoint deleteCandidate : deleteCandidates) {
//			edges.remove(deleteCandidate);
//		 }
//	}

    public WPList getNodes(){
        return nodes;
    }

    public RSList getEdges() {
        return edges;
    }
}
