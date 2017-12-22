package airspaceengine.airspacestructure;

import org.junit.jupiter.api.Test;

import java.io.IOException;


/**
 * Created by Ziji Shi on 20/12/17.
 */
class PlanarAirspaceStructureCreatorTest {

    @Test
    void createAirspaceStructure() throws IOException {
        PlanarAirspaceStructureCreator pc = new PlanarAirspaceStructureCreator();
        AirspaceStructure as = pc.createAirspaceStructure();
    }

}