package test;

import airspaceengine.airspacestructure.PlanarAirspaceStructureCreator;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 2/1/18.
 */
class PlanarAirspaceStructureCreatorTest {
    @Test
    void testCreateAirspaceStructure() throws IOException {
        PlanarAirspaceStructureCreator pasc = new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/demo.json");
        pasc.createAirspaceStructure();
    }

}