package flight_plan;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.routesegment.RouteSegment;
import airspaceengine.waypoint.Waypoint;
import collisionavoidanceengine.flightplan.Flight;
import collisionavoidanceengine.flightplan.FlightSchedule;
import simulationengine.SimulationConfiguration;
import simulationengine.json_formatting.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomFlightPlanCreator implements FlightPlanCreator {
    List<Flight> flights = new ArrayList<>();

    public List<Flight> createFlightPlans(AirspaceStructure airMap, SimulationConfiguration config) {
        Random random = new Random();
        for (int i = 0; i < config.getNumberOfFlights(); i++){
            int randomInt = random.nextInt(50 - 1 + 1) + 1;
            flights.add(new Flight("RQ_"  + String.format("%03d", i + 1), "UV_"  + String.format("%05d", i + 1), randomInt, randomInt));
        }

        int j = 0;
        for (RouteSegment segment : airMap.getEdges().getRouteSegMap().values()) {
            ArrayList<Waypoint> path = new ArrayList<>();
            path.add(segment.getFrom());
            path.add(segment.getTo());
            flights.get(j).setFlightPath(path);
            j++;
            if (j >= config.getNumberOfFlights())
                break;
        }
//        ArrayList<String> path1 = new ArrayList<>();
//        path1.add("WP_B85");
//        path1.add("WP_B97");
//        flights.get(0).setFlightPath(path1);
//        ArrayList<String> path2 = new ArrayList<>();
//        path2.add("WP_THM");
//        path2.add("WP_GMLM");
//        flights.get(1).setFlightPath(path2);
//        ArrayList<String> path3 = new ArrayList<>();
//        path2.add("WP_Y61M");
//        path2.add("WP_Y28M");
//        flights.get(2).setFlightPath(path2);
        return flights;
    }

}
