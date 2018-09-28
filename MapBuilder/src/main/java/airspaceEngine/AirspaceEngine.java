package airspaceEngine;

import airspaceEngine.airspacestructure.AirspaceStructure;
import airspaceEngine.airspacestructure.AirspaceStructureCreator;
import airspaceEngine.airspacestructure.AirspaceStructureFactory;
import config.Config;

import java.io.IOException;

public class AirspaceEngine {
    private AirspaceStructure airMap;

    public AirspaceEngine(){}

    public void createAirspace(Config config) throws IOException {
    		switch(config.airMapType) {
    			case "RANDOM":
    				AirspaceStructureCreator airspaceCreator = AirspaceStructureFactory.getAirspaceStructureCreator("RANDOM", config.pathToMap);
    		        this.airMap = airspaceCreator.createAirspaceStructure();
    		        break;
                case "PLANARGRAPH":
                    AirspaceStructureCreator airspaceC = AirspaceStructureFactory.getAirspaceStructureCreator("PLANARGRAPH", config.pathToMap);
                    this.airMap = airspaceC.createAirspaceStructure();
                    break;
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
