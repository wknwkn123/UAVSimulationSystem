package servers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import mapBuilder.graph.Edge2D;
import mapBuilder.graph.Vector2D;
import mapBuilder.graph.loader.JsonLoader;
import mapBuilder.triangulation.DelaunayTriangulator;
import mapBuilder.triangulation.NotEnoughPointsException;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import flightEngine.FlightGenerationApp;
import flightEngine.json_formatting.Simulation;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Executors;

import static java.lang.Math.abs;
import static java.lang.StrictMath.sqrt;

public class HTTPServer implements Runnable {
    private static HTTPServer instance = new HTTPServer();

    public static HTTPServer getInstance() {
        return instance;
    }

    public HTTPServer() {}

    public void startServer() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        InetSocketAddress addr = new InetSocketAddress(8080);
        HttpServer server = null;
        try {
            server = HttpServer.create(addr, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(server.toString() + addr);
        server.createContext("/api/start", new StartHandle());
        server.createContext("/api/graph", new MyHandler());
        server.createContext("/api/graph/storage", new StorageHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Server is listening on port 8080");
    }
}

class StartHandle implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {

        String requestMethod = exchange.getRequestMethod();
        InputStream in = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

        System.out.println("Server Handling request to Create Flight Plans .....");

        if (requestMethod.equalsIgnoreCase("POST")) {

            String jsonString = IOUtils.toString(reader);
            //String jsonString = "{\"simulationStart\":\"start\",\"simulationParameter\":{\"airspaceType\":\"PLANARGRAPH\",\"flightScheduleType\":\"RANDOM\"}}";
            System.out.print(jsonString);

            //Starting Simulation
            Gson gson = new Gson();
            System.out.print(gson.toJson(jsonString).toString());
            Simulation parameter = gson.fromJson(jsonString, Simulation.class);

            FlightGenerationApp simApp = new FlightGenerationApp(parameter);
            simApp.startSimulation();

            String responseString = " {\"FlightPlanRequest\":\"received\"}";
            exchange.sendResponseHeaders(200, responseString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseString.getBytes());
            os.close();
        }
    }
}

class MyHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);
        InputStream in = exchange.getRequestBody(); //获得输入流
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        System.out.println("READER " +  requestMethod + reader);
        if (requestMethod.equalsIgnoreCase("POST")) {
            String jsonString = IOUtils.toString(reader);//text是我获取的post提交的数据（现在是字符串的形式）
            System.out.print(jsonString.length());
            JsonLoader loader = new JsonLoader();
            Vector<Vector2D> pointSet = loader.loadNodeListWithString(jsonString);
            DelaunayTriangulator delaunayTriangulator = new DelaunayTriangulator(pointSet);
            try {
                delaunayTriangulator.triangulate();
            } catch (NotEnoughPointsException e) {
                e.printStackTrace();
            }
            List<Edge2D> edges = delaunayTriangulator.getEdges();
            JSONArray jsonEdges = new JSONArray();
            for (Edge2D e : edges) {
//            {"source":"N", "target":"I", "meta-data":{"weight":55.23}},
                double weight = sqrt(abs((e.getSource().getX() - e.getTarget().getX()) * (e.getSource().getX() - e.getTarget().getX()) + (e.getSource().getY() - e.getTarget().getY()) * (e.getSource().getY() - e.getTarget().getY())));
                jsonEdges.put((JSONString) () -> "{\"source\":\"" + e.getSource().getId() + "\", \"target\":\"" + e.getTarget().getId() + "\", \"meta-data\":{\"weight\":" + weight + "}}");
//                jsonEdges.put((JSONString) () -> "{\"target\":\"" + e.getSource().getId() + "\", \"source\":\"" + e.getTarget().getId() + "\", \"meta-data\":{\"weight\":" + weight + "}}");
            }
//            System.out.println(loader.writeEdges2JsonString(jsonString, jsonEdges));


            String responseString = loader.writeEdges2JsonString(jsonString, jsonEdges);
            //设置响应头
            exchange.sendResponseHeaders(200, responseString.length());
            OutputStream os = exchange.getResponseBody();
            os.write(responseString.getBytes());
//            System.out.println("asdadasd" + responseString);
            os.close();
//
//
//            Headers responseHeaders = exchange.getResponseHeaders();
//            responseHeaders.set("Content-Type", "application/json");
//            exchange.sendResponseHeaders(200, 0);
//
//            OutputStream responseBody = exchange.getResponseBody();
//            Headers requestHeaders = exchange.getRequestHeaders();
//            Set<String> keySet = requestHeaders.keySet();
//            Iterator<String> iter = keySet.iterator();
//            while (iter.hasNext()) {
//                String key = iter.next();
//                List values = requestHeaders.get(key);
//                String s = key + " = " + values.toString() + "\n";
//                responseBody.write(s.getBytes());
//            }
//            responseBody.close();
        }
    }
}

class StorageHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        InputStream in = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        String jsonString = IOUtils.toString(reader);
        JSONObject jobj = new JSONObject(jsonString);
        System.out.println("READER " +  requestMethod + reader);
        if (requestMethod.equalsIgnoreCase("POST")) {
            String type = jobj.getString("type");
            if (type.equalsIgnoreCase("savefile")){
                String filename = jobj.getString("name") + ".json";
                JSONObject graph = jobj.getJSONObject("graph");
                //save graph as file name = name
                FileWriter writer = null;
                try {
                    writer = new FileWriter("./data/airspace/" + filename);
                    writer.write(graph.toString());
                    String responseString = "File is successfully saved";
                    exchange.sendResponseHeaders(200, responseString.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(responseString.getBytes());
                    os.close();
                } catch (IOException e) {
                    String responseString = "File is not saved. Server error.";
                    exchange.sendResponseHeaders(500, responseString.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(responseString.getBytes());
                    os.close();
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (type.equalsIgnoreCase("getfile")) {
                //get name of file
                String filename = jobj.getString("name") + ".json";
                //return the file from database
                String jsonData = readFile("./data/airspace/" + filename);
                exchange.sendResponseHeaders(200, jsonData.length());
                OutputStream os = exchange.getResponseBody();
                os.write(jsonData.getBytes());
                os.close();
            } else if (type.equalsIgnoreCase("getlist")) {
                File folder = new File("./data/airspace");
                JSONObject fileNames = new JSONObject();
                ArrayList<String> arr = new ArrayList<>();
                //give list file names from directory
                for (final File fileEntry : folder.listFiles()) {
                    arr.add(fileEntry.getName().replace(".json", ""));
                }
                fileNames.put("names", arr);
                String responseString = fileNames.toString();
                exchange.sendResponseHeaders(200, responseString.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseString.getBytes());
                os.close();
            } else {
                JSONObject response = new JSONObject();
                String responseString = "Invalid request";
                response.put("status", responseString);
                exchange.sendResponseHeaders(400, responseString.length());
                OutputStream os = exchange.getResponseBody();
                os.write(responseString.getBytes());
                os.close();
            }
        }
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
