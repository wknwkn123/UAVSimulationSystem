package airspaceengine.airspacestructure;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 3/1/18.
 */
class PlanarAirspaceStructureCreatorTest {
    @Test
    void createAirspaceStructure() {
        PlanarAirspaceStructureCreator pl  = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
        pl.createAirspaceStructure();
    }

}