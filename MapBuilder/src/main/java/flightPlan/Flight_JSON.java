package flightPlan;

import airspaceEngine.waypoint.Waypoint;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.ArrayList;

public class Flight_JSON {
    private String flightID;
    private String requestID;
    private String UAVID;
    private int departTime;
    private int arrivalTime;

    private String waypoints;

    public Flight_JSON(Flight fligth){
        this.UAVID = fligth.getUAVID();
        this.flightID = fligth.getFlightID();
        this.requestID = fligth.getRequestID();
        this.departTime = fligth.getDepartTime();
        this.arrivalTime = fligth.getArrivalTime();


        Gson gson = new Gson();
        ArrayList arr = new ArrayList();
        //waypoints = new ArrayList();
        for(Waypoint way : fligth.getFlightPath()){
            JSONObject obj = new JSONObject();
            arr.add(obj.put("waypoint",gson.toJson(new WayPoint_JSON(way))));
        }
        JSONObject obj = new JSONObject();
        obj.put("waypoints", arr);
        waypoints = obj.toString();//gson.toJson(new WayPoint_JSON(fligth.getFlightPath().get(0)));
        //System.out.println(waypoints);

    }
        public String getFlightId() {
        return this.flightID;
    }

//    public Coordinate getCoordinate() { return coordinate; }
//
//    public void setCoordinate(Coordinate coordinate) {
//        this.coordinate = coordinate;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public void setPlanID(String planID) {
//        this.planID = planID;
//    }
//
//    public String getPlanID() {
//        return planID;
//    }
//
//    public void setTime(double time) {
//        this.time = time;
//    }
//
//    public NodePoint getStartPoint() {
//        return startPoint;
//    }
//
//    public void setStartPoint(NodePoint startPoint) {
//        this.startPoint = startPoint;
//    }
//
//    public NodePoint getEndPoint() {
//        return endPoint;
//    }
//
//    public void setEndPoint(NodePoint endPoint) {
//        this.endPoint = endPoint;
//    }
}
