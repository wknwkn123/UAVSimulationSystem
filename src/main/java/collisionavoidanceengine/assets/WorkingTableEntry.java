package collisionavoidanceengine.assets;

import airspaceengine.waypoint.Waypoint;

/**
 * Created by Ziji Shi on 26/12/17.
 *
 *
 * WorkingTableEntry is just a class that combines a WayPoint with its weight
 */
public class WorkingTableEntry {
    public Waypoint wp;
    // fCost is the total cost. i.e, f = g+h
    public double fCost;
    // gCost is the actually cost so far
    public double gCost;

    public WorkingTableEntry(Waypoint wp, double f, double g){
        this.wp = wp;
        this.fCost = f;
        this.gCost=g;
    }
}
