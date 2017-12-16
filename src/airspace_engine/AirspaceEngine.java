package airspace_engine;

import airspace_engine.airspace_structure.*;

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

    public void createAirspace(String type) {
    		switch(type) {
    			case "RANDOM":
    				AirspaceStructureCreator airspaceCreator = AirspaceStructureFactory.getAirspaceStructureCreator("RANDOM");
    		        setAirMap(airspaceCreator.createAirspaceStructure());
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
