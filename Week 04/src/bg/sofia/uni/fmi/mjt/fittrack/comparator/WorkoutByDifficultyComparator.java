package bg.sofia.uni.fmi.mjt.fittrack.comparator;

import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;

import java.util.Comparator;

public class WorkoutByDifficultyComparator implements Comparator<Workout> {
    @Override
    public int compare(Workout a, Workout b) {
        return Integer.compare(a.getDifficulty(), b.getDifficulty());
    }
}
