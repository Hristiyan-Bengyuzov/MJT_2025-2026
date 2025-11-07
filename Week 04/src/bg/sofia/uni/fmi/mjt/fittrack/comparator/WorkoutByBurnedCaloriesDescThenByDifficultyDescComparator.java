package bg.sofia.uni.fmi.mjt.fittrack.comparator;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

import java.util.Comparator;

public class WorkoutByBurnedCaloriesDescThenByDifficultyDescComparator implements Comparator<Workout> {
    @Override
    public int compare(Workout a, Workout b) {
        int caloriesCompare = Integer.compare(b.getCaloriesBurned(), a.getCaloriesBurned());
        if (caloriesCompare != 0) {
            return caloriesCompare;
        }

        return Integer.compare(b.getDifficulty(), a.getDifficulty());
    }
}
