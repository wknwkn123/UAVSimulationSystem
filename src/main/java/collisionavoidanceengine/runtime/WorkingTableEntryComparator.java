package collisionavoidanceengine.runtime;

import java.util.Comparator;

/**
 * Created by Ziji Shi on 26/12/17.
 */
public class WorkingTableEntryComparator implements Comparator<WorkingTableEntry> {
    @Override
    public int compare(WorkingTableEntry t1, WorkingTableEntry t2) {
        if (t1.fCost-t2.fCost<0)
            return -1;
        else if (t1.fCost-t2.fCost>0)
            return 1;
        else
            return 0;
    }
};