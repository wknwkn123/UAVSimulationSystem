package airspaceengine.airspacestructure;

import java.io.IOException;

public class AirspaceStructureFactory {
    public static AirspaceStructureCreator getAirspaceStructureCreator(String type) throws IOException {
        switch(type) {
            case "PLANARGRAPH":
                // todo: change later
                return new PlanarAirspaceStructureCreator("/Users/StevenShi/Documents/2017Winter-UAV/uavsimulation/data/reduced_singapore_muiti_store_parking.json");
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
