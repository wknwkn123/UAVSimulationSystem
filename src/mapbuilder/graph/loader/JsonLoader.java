package mapbuilder.graph.loader;

import mapbuilder.graph.Edge2D;
import mapbuilder.graph.Vector2D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import mapbuilder.triangulation.Node;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    public static List<Edge2D> loadEdges(String file) throws IOException, JSONException {
        throw new NotImplementedException();
    }

    public static List<Vector2D> loadNodes(String file) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(file)));

        JSONObject jobj = new JSONObject(content);
        ArrayList<Vector2D> pointSet = new ArrayList<>();
        JSONArray arr = jobj.getJSONObject("mapbuilder/graph").getJSONArray("nodes");
        JSONObject node;
        for (int i = 0; i < arr.length(); i++) {
            node = arr.getJSONObject(i).getJSONObject("meta-data");
            Node vec = new Node(node.getInt("x"), node.getInt("y"));
            vec.setId(arr.getJSONObject(i).getString("id"));
            pointSet.add(vec);
        }
        return pointSet;

    }

}
