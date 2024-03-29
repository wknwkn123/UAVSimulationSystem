package simulationengine;

/**
 *
 */
public class Time {
    private static Time ourInstance = new Time();
    private int unit;
    private boolean completed = false;
    private int multiplier = 1;

    public static Time getInstance() {
        return ourInstance;
    }

    private Time() {
        unit = 0;
    }

    public int getUnit() { return unit; }

    public void tick() {
        unit++;
        System.out.println("Time unit: " + unit);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public double getRealTime() {
        return unit/multiplier;
    }
}
