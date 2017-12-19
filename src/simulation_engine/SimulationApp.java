package simulation_engine;

import airspace_engine.AirspaceEngine;
import flight_plan.FlightPlanEngine;

public class SimulationApp {
	
	public static void main(String[] args) {
	    //create airspace
		AirspaceEngine.getInstance().createAirspace("RANDOM");

		//create UAVs


		//create schedule/demand
		FlightPlanEngine.getInstance().createFlightPlans("RANDOM", AirspaceEngine.getInstance().getAirMap());

		//assign schedule to UAVs


        //run simulation
	}
}
