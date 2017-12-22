package collision_avoidance_engine.constants;

/**
 * Created by Ziji Shi on 19/12/17.
 *
 * This is a constant pool for storing the variable we don't want to change at this stage.
 *
 * Unit for Waiting penalty is minutes, for speed is meters per minutes.
 */
public class Constant {
    public static final double BATTERY_LIFE = 30;
    public static final double WAITING_PENALTY_AT_LANDING_NODE = 3;
    public static final double WAITING_PENALTY_AT_NON_LANDING_NODE = 0.3;
    public static final double SPEED = 500;
}
