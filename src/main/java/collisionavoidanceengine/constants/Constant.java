package collisionavoidanceengine.constants;

/**
 * Created by Ziji Shi on 19/12/17.
 *
 * This is a constant pool for storing the variable we don't want to change at this stage.
 *
 * Unit for Waiting penalty is minutes, for speed is meters per minutes.
 */
public class Constant {

    // How long can UAV fly totally
    public static final double BATTERY_LIFE = 30;
    // When the other UAV want to land at the junction, it will wish to keep it clear for a longer period
    public static final double WAITING_PENALTY_AT_LANDING_NODE = 2;
    // When the other UAV just want to pass the node, the waiting time is shorter. This is also the minimum time required to pass a node
    public static final double WAITING_PENALTY_AT_NON_LANDING_NODE = 0.3;
    // Assumes all UAV fly at a constant speed
    public static final double UAV_SPEED = 500;
    // Number of requested flights in a simulation
    public static final int INITIAL_FLIGHT_CAPACITY = 1000;
    // Max allowable delay time
    public static final int MAX_DELAY=15;
    // Spacer for printing
    public static final String SPACER = "       ";
    // Max number of en-routes
    public static final int MAX_CONNECTIONS = 4;

}
