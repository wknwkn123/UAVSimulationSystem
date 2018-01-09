package mapbuilder;

import mapbuilder.graph.Edge2D;
import mapbuilder.graph.Vector2D;
import mapbuilder.graph.loader.JsonLoader;
import mapbuilder.triangulation.DelaunayTriangulator;
import mapbuilder.triangulation.NotEnoughPointsException;
import mapbuilder.triangulation.Triangle2D;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


import static java.lang.Math.abs;
import static java.lang.StrictMath.sqrt;


public class Triangulate {
    public static void main(String[] args) throws IOException, NotEnoughPointsException {
        JsonLoader loader = new JsonLoader();
        String file= args[0];

        Vector<Vector2D> pointSet = loader.loadNodesList(file);
        DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
        delaunayTriangulator.triangulate();
        List<Edge2D> edges= delaunayTriangulator.getEdges();



        JSONArray jsonEdges = new JSONArray();
        for(Edge2D e: edges){
//            {"source":"N", "target":"I", "meta-data":{"weight":55.23}},
            double weight = sqrt(abs((e.getSource().getX() - e.getTarget().getX())*(e.getSource().getX() - e.getTarget().getX()) -(e.getSource().getY() - e.getTarget().getY())*(e.getSource().getY() - e.getTarget().getY())));
            jsonEdges.put((JSONString) () -> "{\"source\":\"" + e.getSource().getId() + "\", \"target\":\"" + e.getTarget().getId() + "\", \"meta-data\":{\"weight\":" + weight + "}}");
            jsonEdges.put((JSONString) () -> "{\"target\":\"" + e.getSource().getId() + "\", \"source\":\"" + e.getTarget().getId() + "\", \"meta-data\":{\"weight\":" + weight + "}}");
        }


        loader.writeEdges2Json(file, jsonEdges);


    }
}
