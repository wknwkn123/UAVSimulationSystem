package airspaceengine;

import airspaceengine.airspacestructure.*;
import config.Config;

import java.io.IOException;

public class AirspaceEngine {
    public AirspaceStructure airMap;
    private static AirspaceEngine singleton;

    public AirspaceEngine(){}

    public static AirspaceEngine getInstance() {
        if(singleton == null) {
            singleton = new AirspaceEngine();
        }
        return singleton;
    }

    public void createAirspace(Config config) throws IOException {
    		switch(config.airMapType) {
    			case "RANDOM":
    				AirspaceStructureCreator airspaceCreator = AirspaceStructureFactory.getAirspaceStructureCreator("RANDOM", config.pathToMap);
    		        this.airMap = airspaceCreator.createAirspaceStructure();
    		        break;
                case "PLANARGRAPH":
                    AirspaceStructureCreator airspaceC = AirspaceStructureFactory.getAirspaceStructureCreator("PLANARGRAPH",null);
                    this.airMap = airspaceC.createAirspaceStructure();
    		    default:
    		        System.out.println("Default is printed. This should not happen.");
    		    	break;
    		}        
    }

    public AirspaceStructure getAirMap() {
        return airMap;
    }

    public void setAirMap(AirspaceStructure airMap) {
        this.airMap = airMap;
    }
}
