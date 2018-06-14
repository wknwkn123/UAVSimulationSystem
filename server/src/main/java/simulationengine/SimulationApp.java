package simulationengine;

import airspaceengine.AirspaceEngine;
import config.Config;
import flight_plan.FlightPlanEngine;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import simulationengine.json_formatting.Simulation;
import uav.UAV;
import websocket.servers.encoder.UAVEncoder;
import websocket.servers.server.Websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Runnable for simulation in order to separate main thread and simulation thread.
 */

public class SimulationApp implements Runnable{
    private boolean stopWork;
    private FlightPlanEngine flightPlanEngine;
    private AirspaceEngine airspaceEngine;
    private SimulationConfiguration configuration;

    public SimulationApp(Simulation param){
        airspaceEngine = new AirspaceEngine();
        configuration = new SimulationConfiguration(param);
        flightPlanEngine = new FlightPlanEngine(this.configuration);
    }

    public void setUAVOrigins() {
        for (UAV uav : flightPlanEngine.getUavEngine().getUAVs()){
            if (uav.getFlightPlans().size() > 0) {
                uav.getOperation().setCurrentX(uav.getFlightPlans().get(0).getFlightPath().get(0).getX());
                uav.getOperation().setCurrentY(uav.getFlightPlans().get(0).getFlightPath().get(0).getY());
                uav.getOperation().setCurrentZ(uav.getFlightPlans().get(0).getFlightPath().get(0).getZ());
            }
        }
    }

    public void startSimulation() {
        this.configuration.setAirspaceType(configuration.getAirspaceType());
        this.configuration.setFlightScheduleType(configuration.getFlightScheduleType());

        //starting simulation
        // create airspace
        try {
            airspaceEngine.createAirspace(new Config());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //create uavs
        flightPlanEngine.getUavEngine().createUAVs("RANDOM");

        //create schedule/demand
        flightPlanEngine.createFlightPlans("RANDOM", airspaceEngine.getAirMap());

        //assign schedule to UAVs
        flightPlanEngine.assignFlightPlans("RANDOMPLAN");

        this.setUAVOrigins();

        flightPlanEngine.getUavEngine().startThread();
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (!this.stopWork) {
            Time.getInstance().setCompleted(false);
            for (int j = 0; j < 200; j++) {
                Time.getInstance().tick();
                RemoteEndpoint remote = Websocket.getInstance().getSession().getRemote();
                ArrayList<String> uavPositions = new ArrayList<>();
                //Blocking Send of a TEXT message to remote endpoint
                try
                {
                    for(UAV uav : flightPlanEngine.getUavEngine().getUAVs()) {
                        uavPositions.add(UAVEncoder.getInstance().encode(uav));
                    }
                    remote.sendString(uavPositions.toString());
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (stopWork)
                    break;
            }
            Time.getInstance().setCompleted(true);
            this.stopWork();
        }
	}

	public void stopWork() {
        this.stopWork = true;
        this.flightPlanEngine.getUavEngine().stopThread();
    }
}
