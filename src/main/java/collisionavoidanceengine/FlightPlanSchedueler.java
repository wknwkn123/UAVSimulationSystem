package collisionavoidanceengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.FlightSchedule;
import flight_plan.FlightPlan;

import java.io.IOException;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanSchedueler {
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan = new FlightSchedule(myAirMap);

    FlightPlanSchedueler(){
        try {
            AirspaceEngine myAirEngine = AirspaceEngine.getInstance();
            myAirEngine.createAirspace("PLANARGRAPH");
            myAirMap=myAirEngine.airMap;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
    }


    public void ScheduleFlight() {



    }
}
