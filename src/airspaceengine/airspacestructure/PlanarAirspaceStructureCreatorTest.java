package airspaceengine.airspacestructure;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Ziji Shi on 20/12/17.
 */
class PlanarAirspaceStructureCreatorTest {

    @org.junit.jupiter.api.Test
    void createAirspaceStructure() throws IOException {
        PlanarAirspaceStructureCreator pc = new PlanarAirspaceStructureCreator();
        AirspaceStructure as = pc.createAirspaceStructure();
    }

}