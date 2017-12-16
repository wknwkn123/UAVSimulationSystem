package airspace_engine.waypoint;

public class Waypoint {

    //instance variables
    private final double x;
    private final double y;
    private final double z;

    //constructor
    public Waypoint(double x_input, double y_input, double z_input) {
        x = x_input;
        y = y_input;
        z = z_input;
    }

    //getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }


}
