package collisionavoidanceengine.request;

import airspaceengine.airspacestructure.AirspaceStructure;
import airspaceengine.airspacestructure.PlanarAirspaceStructureCreator;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 3/1/18.
 */
class RandomRequestCreatorTest {
    @Test
    void generateRequest() {

        PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
        AirspaceStructure airmap = pl.createAirspaceStructure();

        RandomRequestCreator rrc = new RandomRequestCreator();
        PriorityQueue<Request> temp= rrc.generateRequest(1000,airmap);

    }

}