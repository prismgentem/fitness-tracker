package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalorieCalculatorTest {

    private final CalorieCalculator calorieCalculator = new CalorieCalculator();

    @Test
    void shouldApplyMinimumCalorieLimit() {
        UserProfile profile = new UserProfile(80, 120.0, 30.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(1200, targetCalories);
    }
}
