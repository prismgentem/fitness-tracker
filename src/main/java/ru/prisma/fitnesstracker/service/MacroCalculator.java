package ru.prisma.fitnesstracker.service;

import ru.prisma.fitnesstracker.model.MacroResult;
import ru.prisma.fitnesstracker.model.enums.Goal;

public class MacroCalculator {

    public MacroResult calculateMacros(int targetCalories, Goal goal) {
        if (targetCalories < 1200) {
            throw new IllegalArgumentException("Целевая калорийность должна быть не меньше 1200");
        }

        int proteinG = switch (goal) {
            case LOSE -> 120;
            case MAINTAIN -> 110;
            case GAIN -> 130;
        };
        int fatG = 60;
        int carbsCalories = targetCalories - proteinG * 4 - fatG * 9;
        int carbsG = Math.max(0, carbsCalories / 4);

        return new MacroResult(targetCalories, proteinG, fatG, carbsG);
    }
}
