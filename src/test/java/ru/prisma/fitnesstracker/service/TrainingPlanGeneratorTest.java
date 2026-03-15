package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingPlanGeneratorTest {

    private final TrainingPlanGenerator trainingPlanGenerator = new TrainingPlanGenerator();

    @Test
    void shouldGenerateLowActivityLosePlan() {
        List<String> plan = trainingPlanGenerator.generateWeeklyPlan(Goal.LOSE, ActivityLevel.LOW);

        assertEquals(3, plan.size());
        assertTrue(plan.stream().allMatch(day -> day.contains("Cardio")));
    }

    @Test
    void shouldGenerateMediumActivityMaintainPlan() {
        List<String> plan = trainingPlanGenerator.generateWeeklyPlan(Goal.MAINTAIN, ActivityLevel.MEDIUM);

        assertEquals(4, plan.size());
        assertTrue(plan.stream().allMatch(day -> day.contains("Mixed")));
    }

    @Test
    void shouldGenerateHighActivityGainPlan() {
        List<String> plan = trainingPlanGenerator.generateWeeklyPlan(Goal.GAIN, ActivityLevel.HIGH);

        assertEquals(5, plan.size());
        assertTrue(plan.stream().allMatch(day -> day.contains("Strength")));
    }
}
