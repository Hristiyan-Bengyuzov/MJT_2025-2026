package bg.sofia.uni.fmi.mjt.fittrack.workout.filter;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

public class CaloriesWorkoutFilter extends RangeWorkoutFilter {
    protected CaloriesWorkoutFilter(int min, int max) {
        super(min, max);
    }

    @Override
    public boolean matches(Workout workout) {
        if (workout == null) {
            return false;
        }

        return isInRange(workout.getCaloriesBurned());
    }
}
