package airspaceengine.airspacestructure;

public class AirspaceStructureFactory {
    public static AirspaceStructureCreator getAirspaceStructureCreator(String type) {
        switch(type) {
            case "RANDOM":
                return new RandomAirspaceStructureCreator();
            case "PLANARGRAPH":
                return new PlanarAirspaceStructureCreator();
            default:
                System.out.println("Default printed. This should not happen");
                return null;
        }
    }
}
