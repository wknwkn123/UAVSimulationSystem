package airspaceengine.airspacestructure;
import airspaceengine.routesegment.RSMap;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.WPMap;
import airspaceengine.waypoint.Waypoint;

public class AirspaceStructure {
    private  WPMap nodes;
    private  RSMap edges;

    AirspaceStructure(WPMap nodes, RSMap edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    // Use the method implemented in RSMap and WPMap
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

    public int getNodeNumbers(){
        return nodes.getSize();
    }

    public int getEdgeNumbers(){
        return edges.getSize();
    }

    public WPMap getNodes(){
        return nodes;
    }

    public RSMap getEdges() {
        return edges;
    }
}
