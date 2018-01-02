package airspaceengine.airspacestructure;

import java.io.IOException;

public class AirspaceStructureFactory {
    public static AirspaceStructureCreator getAirspaceStructureCreator(String type) throws IOException {
        switch(type) {
            case "PLANARGRAPH":
                return new PlanarAirspaceStructureCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
