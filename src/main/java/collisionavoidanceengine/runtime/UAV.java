package collisionavoidanceengine.runtime;

import static collisionavoidanceengine.constants.Constant.BATTERY_LIFE;

/**
 * Created by Ziji Shi on 19/12/17.
 *
 * A simplified UAV template for testing collision-avoidance algorithm.
 *
 */
public class UAV {
    private int UAV_ID;
    private double BatteryCapacity;
    private double currentX;
    private double currentY;

    public UAV(int ID){
        this.UAV_ID=ID;
        // In this simulation, we assume battery capacity is 30 minutes, but since it will run return trip, we use 15 mins.
        this.BatteryCapacity = BATTERY_LIFE /2;
    }

    public int getUAVID(){
        return this.UAV_ID;
    }

    public double getBatteryCapacity(){
        return this.BatteryCapacity;
    }

    public double getCurrentX(){
        return this.currentX;
    }

    public double getCurrentY(){
        return this.currentY;
    }

}