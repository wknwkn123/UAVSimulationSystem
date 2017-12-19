package simulation_engine;

import airspace_engine.AirspaceEngine;
import flight_plan.FlightPlanEngine;
import uav.UAVEngine;

public class SimulationApp {
	
	public static void main(String[] args) {
	    //create airspace
		AirspaceEngine.getInstance().createAirspace("RANDOM");

		//create UAVs
        UAVEngine.getInstance().createUAVs("RANDOM");

		//create schedule/demand
		FlightPlanEngine.getInstance().createFlightPlans("RANDOM", AirspaceEngine.getInstance().getAirMap());

		//assign schedule to UAVs


        //run simulation
	}
}
