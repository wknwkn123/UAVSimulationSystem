package airspaceengine.airspacestructure;

import airspaceengine.routesegment.RSList;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.WPList;
import airspaceengine.waypoint.Waypoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


/**
 * Created by Ziji Shi on 19/12/17.
 *
 * Generate graph based on demo.json file.
 */

public class PlanarAirspaceStructureCreator implements AirspaceStructureCreator{
    private String pathToMap = "demo.json";
    private String content = new String (Files.readAllBytes(Paths.get(pathToMap)));
    private JSONObject jobj = new JSONObject(content);
    private WPList Nodes = new WPList();
    private RSList Edges = new RSList();

    PlanarAirspaceStructureCreator() throws IOException {
    }

    public AirspaceStructure createAirspaceStructure() throws IOException {

        //create nodes
        JSONArray nodeArr = jobj.getJSONObject("graph").getJSONArray("nodes");
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
        JSONArray edgeArr = jobj.getJSONObject("graph").getJSONArray("edges");
        for (int i = 0; i < edgeArr.length(); i++) {
            JSONObject curObj = edgeArr.getJSONObject(i);
            String edgeID = "RS_"+Integer.toString(i);
            Waypoint origin = this.Nodes.getWaypointByID(curObj.getJSONObject("source").toString());
            Waypoint end = this.Nodes.getWaypointByID(curObj.getJSONObject("end").toString());
            double weight = curObj.getJSONObject("meta-data").getDouble("weight");

            this.Edges.addRouteSegment(new RouteSegment(edgeID,origin,end, weight));

            System.out.println("Route Segment " + edgeID + " initialized");
        }

        //find adjacency relationship between two nodes given an edge; since this is a bigraph, we only need to find half of it.
        for (int i =0; i< this.Edges.getSize()/2;i++){
            // src is adjacent to dest, and vice versa
            Edges.getByIndex(i).getSource().addAdjacentWaypoint(Edges.getByIndex(i).getDestination());
            Edges.getByIndex(i).getDestination().addAdjacentWaypoint(Edges.getByIndex(i).getSource());
        }

        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(Nodes, Edges);
        System.out.println("Airmap created.");
        return airMap;
    }
}
