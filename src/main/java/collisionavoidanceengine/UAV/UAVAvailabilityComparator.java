package collisionavoidanceengine.UAV;

import java.util.Comparator;

/**
 * Created by Ziji Shi on 4/1/18.
 */
public class UAVAvailabilityComparator implements Comparator<UAV>{
    @Override
    public int compare(UAV u1, UAV u2){
        if(u1.getNextAvailableTime()-u2.getNextAvailableTime()<0)
            return -1;
        else if (u1.getNextAvailableTime()-u2.getNextAvailableTime()>0)
            return 1;
        else
            return 0;
    }

}
