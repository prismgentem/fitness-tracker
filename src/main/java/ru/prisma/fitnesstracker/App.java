package ru.prisma.fitnesstracker;

import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;
import ru.prisma.fitnesstracker.service.CalorieCalculator;

public class App {

    public static void main(String[] args) {
        UserProfile profile = new UserProfile(28, 178.0, 74.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);
        CalorieCalculator calorieCalculator = new CalorieCalculator();
        int targetCalories = calorieCalculator.calculateTargetCalories(profile);

        System.out.println("Target calories: " + targetCalories);
    }
}
