package collisionavoidanceengine;

import airspaceengine.AirspaceEngine;
import airspaceengine.airspacestructure.AirspaceStructure;
import collisionavoidanceengine.flightplan.FlightSchedule;
import collisionavoidanceengine.request.Request;
import collisionavoidanceengine.request.RequestCreatorSelector;

import java.io.IOException;
import java.util.PriorityQueue;

import static collisionavoidanceengine.constants.Constant.INITIAL_FLIGHT_CAPACITY;

/**
 * Created by StevenShi on 17/12/17.
 */
public class FlightPlanSchedueler {
    public int currentTime=0;
    public PriorityQueue<Request> myRequestQ;
    public AirspaceStructure myAirMap ;
    public FlightSchedule currentFlightPlan = new FlightSchedule(myAirMap);

    FlightPlanSchedueler(String airMapType, String requestQueueTyoe){
        // Initialization
        try {
            AirspaceEngine myAirEngine = AirspaceEngine.getInstance();
            // TODO:to be substituted with airMapType
            myAirEngine.createAirspace("PLANARGRAPH");
            myAirMap=myAirEngine.airMap;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("ERROR : CANNOT CREATE AIRMAP!");
        }
        // Notice that request must be initialized after AirMap is created
        // This is because RequestQueue will require the topology of AirMap
        try{
            // to be substituted with requestQueueTyoe
            RequestCreatorSelector rcs = new RequestCreatorSelector();
            rcs.setRequestCreator("RANDOM");
            myRequestQ = rcs.getRequestCreator().generateRequest(INITIAL_FLIGHT_CAPACITY,myAirMap);
        }
        catch (Exception e){
            e.printStackTrace();;
            System.out.printf("ERROR: CANNOT CREATE REQUEST QUEUE!");
        }
    }


    public void ScheduleFlight(){
        currentTime=0;
        while(!myRequestQ.isEmpty()){
            Request currentRequest = myRequestQ.poll();
//            Map<Integer,String> flightPath = new Map.Entry<Integer, String>();

        }
    }
}
