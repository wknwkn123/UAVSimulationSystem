package simulation_engine;

import uav.UAVEngine;

import java.util.concurrent.TimeUnit;

public class SimulationApp implements Runnable{
    private boolean stopWork;
    private static SimulationApp instance = new SimulationApp();

    public static SimulationApp getInstance() {
        return instance;
    }

    public SimulationApp(){}

    public void run() {
        while (!stopWork) {
            Time.getInstance().setCompleted(false);
            for (int j = 0; j < 25; j++) {
                Time.getInstance().tick();
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Time.getInstance().setCompleted(true);
            this.stopWork();
        }
	}

	public void stopWork() {
        stopWork = true;
        UAVEngine.getInstance().stopThread();
    }
}
