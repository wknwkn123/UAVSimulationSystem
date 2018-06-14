package mapbuilder.graph.loader;

import mapbuilder.graph.Edge2D;
import mapbuilder.graph.Vector2D;
import mapbuilder.triangulation.Node;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class JsonLoader {
    public List<Edge2D> loadEdges(String file) throws IOException, JSONException {
        throw new NotImplementedException();
    }

    public Vector<Vector2D> loadNodesList(String file) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(file)));

        JSONObject jobj = new JSONObject(content);
        Vector<Vector2D> pointSet = new Vector<>();
        JSONArray arr = jobj.getJSONObject("graph").getJSONArray("nodes");
        JSONObject node;
        for (int i = 0; i < arr.length(); i++) {
            node = arr.getJSONObject(i).getJSONObject("meta-data");
            Node vec = new Node(node.getInt("x"), node.getInt("y"));
            vec.setId(arr.getJSONObject(i).getString("id"));
            pointSet.add(vec);
        }
        return pointSet;
    }

    public Vector<Vector2D> loadNodeListWithString(String jsonString) {

        JSONObject jobj = new JSONObject(jsonString);
        Vector<Vector2D> pointSet = new Vector<>();
        JSONArray arr = jobj.getJSONObject("graph").getJSONArray("nodes");
        JSONObject node;
        for (int i = 0; i < arr.length(); i++) {
            node = arr.getJSONObject(i).getJSONObject("meta-data");
            Node vec = new Node(node.getInt("x"), node.getInt("y"));
            vec.setId(arr.getJSONObject(i).getString("id"));
            pointSet.add(vec);
        }
        return pointSet;
    }

    public String writeEdges2JsonString(String jsonString, JSONArray jsonEdges) throws IOException {

        JSONObject jobj = new JSONObject(jsonString);
        jobj.getJSONObject("graph").put("edges", jsonEdges);
        return jobj.toString();


    }
    public Map<String,Vector2D> loadNodesMap(String file) throws IOException, JSONException {
        String content = new String(Files.readAllBytes(Paths.get(file)));

        JSONObject jobj = new JSONObject(content);
        Map<String,Vector2D> pointMap = new HashMap<>();
        JSONArray arr = jobj.getJSONObject("graph").getJSONArray("nodes");
        JSONObject node;
        for (int i = 0; i < arr.length(); i++) {
            String id = arr.getJSONObject(i).getString("id");
            node = arr.getJSONObject(i).getJSONObject("meta-data");

            Node vec = new Node(node.getInt("x"), node.getInt("y"));
            vec.setId(id);
            pointMap.put(id ,vec);
        }
        return pointMap;

    }

    public void writeEdges2Json(String file, JSONArray jsonEdges) throws IOException {

        String content = new String(Files.readAllBytes(Paths.get(file)));
        JSONObject jobj = new JSONObject(content);
        jobj.getJSONObject("graph").put("edges", jsonEdges);

        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jobj.toString());
            fileWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}