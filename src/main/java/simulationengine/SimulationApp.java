package simulationengine;

import collisionavoidanceengine.FlightPlanScheduler;
import config.Config;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import uav.UAV;
import uav.UAVEngine;
import websocket.simple_v2.encoder.UAVEncoder;
import websocket.simple_v2.server.Websocket;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Singleton runnable for simulation in order to separate main thread and simulation thread.
 */

public class SimulationApp implements Runnable{
    private boolean stopWork;
    private static SimulationApp instance = new SimulationApp();

    public static SimulationApp getInstance() {
        return instance;
    }

    public SimulationApp(){}

    public void run() {
        while (!this.stopWork) {
            Time.getInstance().setCompleted(false);
            for (int j = 0; j < 200; j++) {
                Time.getInstance().tick();
                RemoteEndpoint remote = Websocket.getInstance().getSession().getRemote();
                //Blocking Send of a TEXT message to remote endpoint
                try
                {
                    for(UAV uav : UAVEngine.getInstance().getUAVs()) {
                        remote.sendString(UAVEncoder.getInstance().encode(uav));
                    }
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
        UAVEngine.getInstance().stopThread();
    }
}
