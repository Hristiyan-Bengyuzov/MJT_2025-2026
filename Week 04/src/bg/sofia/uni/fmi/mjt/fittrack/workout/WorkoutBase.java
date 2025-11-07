package bg.sofia.uni.fmi.mjt.fittrack.workout;

import bg.sofia.uni.fmi.mjt.fittrack.exception.InvalidWorkoutException;

public abstract class WorkoutBase {
    private static final int DIFFICULTY_LOWER_BOUND = 1;
    private static final int DIFFICULTY_UPPER_BOUND = 5;

    private final String name;
    private final int duration;
    private final int caloriesBurned;
    private final int difficulty;

    protected WorkoutBase(String name, int duration, int caloriesBurned, int difficulty) {
        if (name == null || name.isBlank()) {
            throw new InvalidWorkoutException("Name cannot be null or blank.");
        }

        if (duration <= 0) {
            throw new InvalidWorkoutException("Duration must be positive.");
        }

        if (caloriesBurned <= 0) {
            throw new InvalidWorkoutException("Calories burned must be positive.");
        }

        if (difficulty < DIFFICULTY_LOWER_BOUND || difficulty > DIFFICULTY_UPPER_BOUND) {
            throw new InvalidWorkoutException("Difficulty must be in range [1, 5].");
        }

        this.name = name;
        this.duration = duration;
        this.caloriesBurned = caloriesBurned;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
