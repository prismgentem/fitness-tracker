package ru.prisma.fitnesstracker.service;

import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;

public class CalorieCalculator {

    private final ProfileValidator profileValidator;

    public CalorieCalculator() {
        this(new ProfileValidator());
    }

    public CalorieCalculator(ProfileValidator profileValidator) {
        this.profileValidator = profileValidator;
    }

    public int calculateTargetCalories(UserProfile profile) {
        profileValidator.validate(profile);

        double age = profile.getAge();
        double height = profile.getHeightCm();
        double weight = profile.getWeightKg();

        double bmr;
        if (profile.getSex() == Sex.M) {
            bmr = 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            bmr = 10 * weight + 6.25 * height - 5 * age - 161;
        }

        double tdee = bmr * resolveActivityMultiplier(profile.getActivityLevel());
        double targetCalories = applyGoalAdjustment(tdee, profile.getGoal());

        if (targetCalories < 1200) {
            targetCalories = 1200;
        }

        return (int) Math.round(targetCalories);
    }

    private double resolveActivityMultiplier(ActivityLevel activityLevel) {
        return switch (activityLevel) {
            case LOW -> 1.2;
            case MEDIUM -> 1.55;
            case HIGH -> 1.725;
        };
    }

    private double applyGoalAdjustment(double tdee, Goal goal) {
        return switch (goal) {
            case LOSE -> tdee - 400;
            case MAINTAIN -> tdee;
            case GAIN -> tdee + 300;
        };
    }
}
