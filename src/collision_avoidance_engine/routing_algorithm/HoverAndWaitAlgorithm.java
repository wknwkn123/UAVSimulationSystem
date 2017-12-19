package collision_avoidance_engine.routing_algorithm;

/**
 * Created by StevenShi on 17/12/17.
 *
 * Routing algorithm 1: Stop and Wait
 *
 * We assume that in the planning stage, when an UAV arrives at some edges
 * that is already in use, it will stop and wait there. The waiting times are as following:
 *
 *      when there is another UAV crossing the same junction (but not landing) : 0.3 minutes
 *           there is another UAV flying on the edge that is a part of path:    wait until it leaves
 *           there is another UAV landing at a station :                        2 minutes (the landing
 *           UAV should land first, then give its way to the passing UAV, then take-off again)
 *
 * In the A* algorithm, we let the time elapsed to be the actual cost, the waiting time at
 * each edge/note to be the heuristics (penalty). If the total time is greater than 15mins, we will use
 * transferable delivery.
 */
public class HoverAndWaitAlgorithm {

}
