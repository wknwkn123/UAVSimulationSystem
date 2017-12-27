package collisionavoidanceengine.assets;

import java.util.Comparator;

/**
 * Created by Ziji Shi on 26/12/17.
 */
public class ThreadComparator implements Comparator<Thread> {
    @Override
    public int compare(Thread t1, Thread t2) {
        if (t1.fCost-t2.fCost<0)
            return -1;
        else if (t1.fCost-t2.fCost>0)
            return 1;
        else
            return 0;
    }
};