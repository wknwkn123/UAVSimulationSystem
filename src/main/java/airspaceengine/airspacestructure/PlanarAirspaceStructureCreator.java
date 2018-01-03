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
 * Generate a 2D AirMap graph based on input json file.
 */

public class PlanarAirspaceStructureCreator implements AirspaceStructureCreator{
    private String pathToMap;
    private String content ;
    private JSONObject jobj;
    private WPMap Nodes;
    private RSMap Edges;

    public PlanarAirspaceStructureCreator(String pathToMapJSON) {
        //pathToMapJSON = "/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/demo.json"
        pathToMap = pathToMapJSON;
        try {
            content = new String (Files.readAllBytes(Paths.get(pathToMap)));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("ERROR READING MAP FILE!");
        }
        jobj = new JSONObject(content);
        Nodes = new WPMap();
        Edges = new RSMap();
    }

    public AirspaceStructure createAirspaceStructure() {

        //create nodes
        JSONArray nodeArr = jobj.getJSONObject("graph").getJSONArray("nodes");
        for (int i = 0; i < nodeArr.length(); i++) {
            JSONObject curObj = nodeArr.getJSONObject(i);

            String nodeID = "WP_"+curObj.getString("id");
            boolean isTransferable = curObj.getString("id").equals("transferable");
            double x_input = curObj.getJSONObject("meta-data").getDouble("x");
            double y_input = curObj.getJSONObject("meta-data").getDouble("y");
            double z_input = 20;

            Waypoint curNode = new Waypoint(nodeID,isTransferable,x_input,y_input,z_input);
            this.Nodes.addWaypoint(curNode);

            System.out.println("Waypoint " + nodeID + " initialized");
        }

        //create edges
        JSONArray edgeArr = jobj.getJSONObject("graph").getJSONArray("edges");
        for (int i = 0; i < edgeArr.length(); i++) {

            JSONObject curObj = edgeArr.getJSONObject(i);

            String edgeID = "RS_"+curObj.getString("source")+"-"+curObj.getString("target");
            String originStr = "WP_"+curObj.getString("source");
            String destStr = "WP_"+curObj.getString("target");

            Waypoint origin = this.Nodes.getWaypointByID(originStr);
            Waypoint end = this.Nodes.getWaypointByID(destStr);

            // weight = time  = distance/speed , notice that distance is measured in hundred meters
            double weight = curObj.getJSONObject("meta-data").getDouble("weight")*100/UAV_SPEED;

            this.Edges.addRouteSegment(new RouteSegment(edgeID,origin,end, weight));
            System.out.println("Route Segment " + edgeID + " initialized");

            //find parental relationship between two nodes given an edge
            origin.addAdjacentWaypoint(end);
        }


        //create airspace
        AirspaceStructure airMap = new AirspaceStructure(Nodes, Edges);
        System.out.println("Airmap created.");
        System.out.println();
        System.out.println();
        return airMap;
    }
}
