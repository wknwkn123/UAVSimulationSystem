package airspaceengine.airspacestructure;

import java.io.IOException;

public class AirspaceStructureFactory {
    public static AirspaceStructureCreator getAirspaceStructureCreator(String type, String pathToMap) throws IOException {
        switch(type) {
            case "PLANAR":
                // pathToMap could be "data/input/AirSpaceMap2D.json"
                return new PlanarAirspaceStructureCreator(pathToMap);
            case "RANDOM":
                return new RandomAirspaceStructureCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
