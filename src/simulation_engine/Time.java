package simulation_engine;

public class Time {
    private static Time ourInstance = new Time();
    private int unit;

    public static Time getInstance() {
        return ourInstance;
    }

    private Time() {
        unit = 0;
    }

    public int getUnit() { return unit; }
}
