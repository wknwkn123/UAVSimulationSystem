package flight_plan;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.Flight;
import collisionavoidanceengine.flightplan.FlightSchedule;

import java.util.ArrayList;
import java.util.List;

public class RandomFlightPlanCreator implements FlightPlanCreator {
//    List<FlightPlan> flightPlans = new ArrayList<>();
    List<Flight> flights = new ArrayList<>();

    public List<Flight> createFlightPlans(AirspaceStructure airMap) {
        flights.add(new Flight("FL_00001", "RQ_001", "UV_000001", 0, 5));
        flights.add(new Flight("FL_00002", "RQ_002", "UV_000002", 2, 6));
        flights.add(new Flight("FL_00003", "RQ_003", "UV_000003", 4, 10));
        ArrayList<String> path1 = new ArrayList<>();
        path1.add("WP_B85");
        path1.add("WP_B97");
        flights.get(0).setFlightPath(path1);
        ArrayList<String> path2 = new ArrayList<>();
        path2.add("WP_THM");
        path2.add("WP_GMLM");
        flights.get(1).setFlightPath(path2);
        ArrayList<String> path3 = new ArrayList<>();
        path2.add("WP_Y61M");
        path2.add("WP_Y28M");
        flights.get(2).setFlightPath(path2);
        return flights;

//        //hardcoding flight plans
//        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00002"), 5, 0, 5));
//        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00003"), airMap.getNodes().getWaypointByID("WP_00005"), 2, 2, 4));
//        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00004"), airMap.getNodes().getWaypointByID("WP_00006"), 2, 5, 8));
//        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00003"), 4, 5, 9));
//        flightPlans.add(new FlightPlan(airMap.getNodes().getWaypointByID("WP_00001"), airMap.getNodes().getWaypointByID("WP_00002"), 3, 10, 13));
//
//        //hardcoding flight path for each plan
//        flightPlans.get(0).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));
//
//        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00006")));
//        flightPlans.get(1).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));
//
//        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00010")));
//        flightPlans.get(2).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));
//
//        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));
//        flightPlans.get(3).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00002")));
//
//        flightPlans.get(4).addFlightSegment(new FlightSegment(airMap.getEdges().getRouteSegmentByID("RS_00001")));
//        return flightPlans;
    }

}
