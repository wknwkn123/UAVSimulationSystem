package collisionavoidanceengine.flightplan;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.airspacestructure.PlanarAirspaceStructureCreator;
import collisionavoidanceengine.request.RandomRequestCreator;
import collisionavoidanceengine.request.Request;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;


/**
 * Created by Ziji Shi on 3/1/18.
 */
class FlightScheduleTest {

    @Test
    void getWaitingPenaltyAtNode() {
        PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
        AirspaceStructure airmap = pl.createAirspaceStructure();

        RandomRequestCreator rrc = new RandomRequestCreator();
        PriorityQueue<Request> temp= rrc.generateRequest(1000,airmap);

        FlightSchedule fp = new FlightSchedule(airmap);

        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtNode("WP_MP5M",231.0))+'\n');
        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtNode("WP_RHM3",231.0))+'\n');
        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtNode("WP_TM46",231.0))+'\n');
    }

    @Test
    void getWaitingPenaltyAtEdge() {

        PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
        AirspaceStructure airmap = pl.createAirspaceStructure();

        RandomRequestCreator rrc = new RandomRequestCreator();
        PriorityQueue<Request> temp= rrc.generateRequest(10000,airmap);

        FlightSchedule fp = new FlightSchedule(airmap);

        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtEdge("RS_HG95-IABY",231.0))+'\n');
        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtEdge("RS_SB14-SB38",231.0))+'\n');
        System.out.printf(String.valueOf(fp.getWaitingPenaltyAtEdge("RS_SB38-Y54M",231.0))+'\n');
    }

}