package airspaceengine;

import airspaceengine.airspacestructure.*;

import java.io.IOException;

public class AirspaceEngine {
    private AirspaceStructure airMap;
    private static AirspaceEngine singleton;

    protected AirspaceEngine(){}

    public static AirspaceEngine getInstance() {
        if(singleton == null) {
            singleton = new AirspaceEngine();
        }
        return singleton;
    }

    public void createAirspace(String type) throws IOException {
    		switch(type) {
//    			case "RANDOM":
//    				AirspaceStructureCreator airspaceCreator = AirspaceStructureFactory.getAirspaceStructureCreator("RANDOM");
//    		        //airMap = airspaceCreator.createAirspaceStructure();
//    		        break;
                case "PLANARGRAPH":
                    AirspaceStructureCreator airspaceC = AirspaceStructureFactory.getAirspaceStructureCreator("PLANARGRAPH");
                    airMap = airspaceC.createAirspaceStructure();
    		    default:
    		    		System.out.println("Default is printed. This should not happen.");
    		    		break;
    		}        
    }
}
