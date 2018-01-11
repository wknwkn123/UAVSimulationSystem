package airspaceengine.airspacestructure;

import airspaceengine.routesegment.RSMap;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.WPMap;
import airspaceengine.waypoint.Waypoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONArray;

import static collisionavoidanceengine.constants.Constant.UAV_SPEED;


/**
 * Created by Ziji Shi on 19/12/17.
 *
 * Generate mapbuilder.graph based on demo.json file.
 */

public class PlanarAirspaceStructureCreator implements AirspaceStructureCreator{
    private String pathToMap = "demo.json";
    private String content = new String (Files.readAllBytes(Paths.get(pathToMap)));
    private JSONObject jobj = new JSONObject(content);
    private WPMap Nodes = new WPMap();
    private RSMap Edges = new RSMap();

    PlanarAirspaceStructureCreator() throws IOException {
    }

    public AirspaceStructure createAirspaceStructure() throws IOException {

        //create nodes
        JSONArray nodeArr = jobj.getJSONObject("main/java/mapbuilder/graph").getJSONArray("nodes");
        for (int i = 0; i < nodeArr.length(); i++) {
            JSONObject curObj = nodeArr.getJSONObject(i);

            String nodeID = "WP_"+curObj.getJSONObject("id").toString();
            boolean isTransferable = curObj.getJSONObject("id").toString().equals("transferable");
            double x_input = curObj.getJSONObject("meta-data").getDouble("x");
            double y_input = curObj.getJSONObject("meta-data").getDouble("y");
            double z_input = 20;

            Waypoint curNode = new Waypoint(nodeID,isTransferable,x_input,y_input,z_input);
            this.Nodes.addWaypoint(curNode);

            System.out.println("Waypoint " + nodeID + " initialized");
        }

        //create edges.
        JSONArray edgeArr = jobj.getJSONObject("/main/java/mapbuilder/graph").getJSONArray("edges");
        for (int i = 0; i < edgeArr.length(); i++) {
            JSONObject curObj = edgeArr.getJSONObject(i);
            String edgeID = "RS_"+Integer.toString(i);
            Waypoint origin = this.Nodes.getWaypointByID(curObj.getJSONObject("source").toString());
            Waypoint end = this.Nodes.getWaypointByID(curObj.getJSONObject("end").toString());
            // weight = time  = distance/speed , notice that distance is measured in hundred meters
            double weight = curObj.getJSONObject("meta-data").getDouble("weight")*100/UAV_SPEED;

            this.Edges.addRouteSegment(new RouteSegment(edgeID,origin,end, weight));

            System.out.println("Route Segment " + edgeID + " initialized");
        }

        //find adjacency relationship between two nodes given an edge; since this is a bi-graph, we only need to find half of it.
        System.out.printf("Now initializing the adjacency nodes");
        for (Map.Entry<String, RouteSegment> entry : Edges.getRouteSegMap().entrySet()){
            Waypoint currentSrc = entry.getValue().getSource();
            Waypoint currentDest = entry.getValue().getDestination();
            // src is adjacent to dest
            currentSrc.addAdjacentWaypoint(currentDest);
        }

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(Nodes, Edges);
        System.out.println("Airmap created.");
        return airMap;
    }
}
