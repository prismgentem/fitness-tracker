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
    void shouldCalculateTargetCaloriesForMaleMaintainMedium() {
        UserProfile profile = new UserProfile(28, 178.0, 72.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(expectedCalories(profile), targetCalories);
    }

    @Test
    void shouldCalculateTargetCaloriesForFemaleGainHigh() {
        UserProfile profile = new UserProfile(30, 165.0, 60.0, Sex.F, ActivityLevel.HIGH, Goal.GAIN);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(expectedCalories(profile), targetCalories);
    }

    @Test
    void shouldCalculateTargetCaloriesForMaleLoseLow() {
        UserProfile profile = new UserProfile(35, 180.0, 85.0, Sex.M, ActivityLevel.LOW, Goal.LOSE);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(expectedCalories(profile), targetCalories);
    }

    @Test
    void shouldApplyMinimumCalorieLimit() {
        UserProfile profile = new UserProfile(80, 120.0, 30.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        assertEquals(1200, targetCalories);
    }

    @Test
    void shouldUseCorrectMediumActivityFactor() {
        UserProfile profile = new UserProfile(30, 180.0, 80.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        int actual = calorieCalculator.calculateTargetCalories(profile);

        double bmr = 10 * 80.0 + 6.25 * 180.0 - 5 * 30 + 5;
        double expected = bmr * 1.55;
        int expectedRounded = (int) Math.round(expected);

        assertEquals(expectedRounded, actual);
    }

    private int expectedCalories(UserProfile profile) {
        double bmr;
        if (profile.getSex() == Sex.M) {
            bmr = 10 * profile.getWeightKg() + 6.25 * profile.getHeightCm() - 5 * profile.getAge() + 5;
        } else {
            bmr = 10 * profile.getWeightKg() + 6.25 * profile.getHeightCm() - 5 * profile.getAge() - 161;
        }

        double activityMultiplier = switch (profile.getActivityLevel()) {
            case LOW -> 1.2;
            case MEDIUM -> 1.55;
            case HIGH -> 1.725;
        };

        double target = switch (profile.getGoal()) {
            case LOSE -> bmr * activityMultiplier - 400;
            case MAINTAIN -> bmr * activityMultiplier;
            case GAIN -> bmr * activityMultiplier + 300;
        };

        if (target < 1200) {
            target = 1200;
        }

        return (int) Math.round(target);
    }
}
