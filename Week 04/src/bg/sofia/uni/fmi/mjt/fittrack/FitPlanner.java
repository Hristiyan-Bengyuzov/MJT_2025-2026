package bg.sofia.uni.fmi.mjt.fittrack;

import bg.sofia.uni.fmi.mjt.fittrack.comparator.WorkoutByBurnedCaloriesDescComparator;
import bg.sofia.uni.fmi.mjt.fittrack.comparator.WorkoutByBurnedCaloriesDescThenByDifficultyDescComparator;
import bg.sofia.uni.fmi.mjt.fittrack.comparator.WorkoutByDifficultyComparator;
import bg.sofia.uni.fmi.mjt.fittrack.exception.OptimalPlanImpossibleException;
import bg.sofia.uni.fmi.mjt.fittrack.workout.Workout;
import bg.sofia.uni.fmi.mjt.fittrack.workout.WorkoutType;
import bg.sofia.uni.fmi.mjt.fittrack.workout.filter.WorkoutFilter;

import java.util.*;

public class FitPlanner implements FitPlannerAPI {
    private final Set<Workout> availableWorkouts;

    public FitPlanner(Collection<Workout> workouts) {
        if (workouts == null) {
            throw new IllegalArgumentException("Workouts cannot be null.");
        }

        this.availableWorkouts = Set.copyOf(workouts);
    }

    @Override
    public List<Workout> findWorkoutsByFilters(List<WorkoutFilter> filters) {
        if (filters == null) {
            throw new IllegalArgumentException("Filters cannot be null.");
        }

        List<Workout> filtered = new ArrayList<>();

        for (Workout workout : availableWorkouts) {
            boolean matchesAll = true;

            for (WorkoutFilter filter : filters) {
                if (!filter.matches(workout)) {
                    matchesAll = false;
                    break;
                }
            }

            if (matchesAll) {
                filtered.add(workout);
            }
        }

        return filtered;
    }

    @Override
    public List<Workout> generateOptimalWeeklyPlan(int totalMinutes) throws OptimalPlanImpossibleException {
        if (totalMinutes < 0) {
            throw new IllegalArgumentException("Total minutes cannot be negative");
        }

        if (totalMinutes == 0) {
            return new ArrayList<>();
        }

        List<Workout> workoutList = new ArrayList<>(availableWorkouts);
        int n = workoutList.size();
        int[][] dp = new int[n + 1][totalMinutes + 1];

        for (int i = 1; i <= n; i++) {
            Workout currentWorkout = workoutList.get(i - 1);
            int duration = currentWorkout.getDuration();
            int calories = currentWorkout.getCaloriesBurned();

            for (int w = 0; w <= totalMinutes; w++) {
                if (duration <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - duration] + calories);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        if (dp[n][totalMinutes] == 0) {
            boolean hasValidWorkout = false;
            for (Workout workout : workoutList) {
                if (workout.getDuration() <= totalMinutes) {
                    hasValidWorkout = true;
                    break;
                }
            }

            if (!hasValidWorkout) {
                throw new OptimalPlanImpossibleException("No valid plan can be generated with given time limit.");
            }
        }

        List<Workout> selected = new ArrayList<>();
        int remainingTime = totalMinutes;

        for (int i = n; i > 0 && remainingTime > 0; i--) {
            if (dp[i][remainingTime] != dp[i - 1][remainingTime]) {
                Workout selectedWorkout = workoutList.get(i - 1);
                selected.add(selectedWorkout);
                remainingTime -= selectedWorkout.getDuration();
            }
        }

        Collections.sort(selected, new WorkoutByBurnedCaloriesDescThenByDifficultyDescComparator());
        return selected;
    }

    @Override
    public Map<WorkoutType, List<Workout>> getWorkoutsGroupedByType() {
        Map<WorkoutType, List<Workout>> groupedWorkouts = new HashMap<>();

        for (Workout workout : availableWorkouts) {
            WorkoutType type = workout.getType();

            groupedWorkouts.putIfAbsent(type, new ArrayList<>());
            groupedWorkouts.get(type).add(workout);
        }

        return Collections.unmodifiableMap(groupedWorkouts);
    }

    @Override
    public List<Workout> getWorkoutsSortedByCalories() {
        List<Workout> sortedWorkouts = new ArrayList<>(availableWorkouts);
        Collections.sort(sortedWorkouts, new WorkoutByBurnedCaloriesDescComparator());
        return Collections.unmodifiableList(sortedWorkouts);
    }

    @Override
    public List<Workout> getWorkoutsSortedByDifficulty() {
        List<Workout> sortedWorkouts = new ArrayList<>(availableWorkouts);
        Collections.sort(sortedWorkouts, new WorkoutByDifficultyComparator());
        return Collections.unmodifiableList(sortedWorkouts);
    }

    @Override
    public Set<Workout> getUnmodifiableWorkoutSet() {
        return this.availableWorkouts;
    }
}
