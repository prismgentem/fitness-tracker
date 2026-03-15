package ru.prisma.fitnesstracker.service;

import java.util.ArrayList;
import java.util.List;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;

public class TrainingPlanGenerator {

    public List<String> generateWeeklyPlan(Goal goal, ActivityLevel level) {
        int trainingDays = switch (level) {
            case LOW -> 3;
            case MEDIUM -> 4;
            case HIGH -> 5;
        };

        String workoutLabel = switch (goal) {
            case LOSE -> "Cardio emphasis";
            case MAINTAIN -> "Mixed";
            case GAIN -> "Strength emphasis";
        };

        List<String> plan = new ArrayList<>();
        for (int day = 1; day <= trainingDays; day++) {
            plan.add("Day " + day + ": " + workoutLabel + " workout " + day);
        }
        return plan;
    }
}
