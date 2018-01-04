package collisionavoidanceengine.UAV;

import static collisionavoidanceengine.constants.Constant.BATTERY_LIFE;

/**
 * Created by Ziji Shi on 19/12/17.
 *
 * A simplified UAV template for testing collision-avoidance algorithm.
 *
 */
public class UAV {
    private int UAV_ID;
    private double nextAvailableTime;
    private double currentX;
    private double currentY;

    public UAV(int ID){
        this.UAV_ID=ID;
        this.nextAvailableTime = 0;
    }

    public int getUAVID(){
        return this.UAV_ID;
    }

    public double getNextAvailableTime(){
        return this.nextAvailableTime;
    }

    public double getCurrentX(){
        return this.currentX;
    }

    public double getCurrentY(){
        return this.currentY;
    }

}