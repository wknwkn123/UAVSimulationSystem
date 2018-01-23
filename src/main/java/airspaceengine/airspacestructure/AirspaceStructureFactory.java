package airspaceengine.airspacestructure;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AirspaceStructureFactory {
    public static AirspaceStructureCreator getAirspaceStructureCreator(String type) throws IOException {
        switch(type) {
            case "PLANARGRAPH":
                // todo: change later
                Path p = Paths.get("data/reduced_singapore_muiti_store_parking.json");
                return new PlanarAirspaceStructureCreator(p.toAbsolutePath().toString());
            case "RANDOM":
                return new RandomAirspaceStructureCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
