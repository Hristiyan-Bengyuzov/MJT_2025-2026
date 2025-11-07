package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

public abstract class RangeWorkoutFilter implements WorkoutFilter {
    protected final int min;
    protected final int max;

    protected RangeWorkoutFilter(int min, int max) {
        if (min < 0) {
            throw new IllegalArgumentException("Min cannot be negative.");
        }

        if (max < 0) {
            throw new IllegalArgumentException("Max cannot be negative.");
        }

        if (min > max) {
            throw new IllegalArgumentException("Min cannot be more than max.");
        }
        
        this.min = min;
        this.max = max;
    }

    protected boolean isInRange(int value) {
        return value >= min && value <= max;
    }
}
