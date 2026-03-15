package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalorieCalculatorTest {

    private final CalorieCalculator calorieCalculator = new CalorieCalculator();

    @Test
    void shouldCalculateTargetCaloriesForMaleProfile() {
        UserProfile profile = new UserProfile(30, 180.0, 80.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertTrue(targetCalories > 1200);
    }

    @Test
    void shouldCalculateTargetCaloriesForFemaleProfile() {
        UserProfile profile = new UserProfile(27, 165.0, 60.0, Sex.F, ActivityLevel.HIGH, Goal.GAIN);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertTrue(targetCalories > 1200);
    }

    @Test
    void shouldApplyMinimumCalorieLimit() {
        UserProfile profile = new UserProfile(80, 120.0, 30.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(1200, targetCalories);
    }
}
