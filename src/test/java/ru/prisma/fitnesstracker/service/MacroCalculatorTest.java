package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.MacroResult;
import ru.prisma.fitnesstracker.model.enums.Goal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MacroCalculatorTest {

    private final MacroCalculator macroCalculator = new MacroCalculator();

    @Test
    void shouldCalculateMacrosForLoseGoal() {
        MacroResult result = macroCalculator.calculateMacros(2000, Goal.LOSE);

        assertEquals(2000, result.getCalories());
        assertEquals(120, result.getProteinG());
        assertEquals(60, result.getFatG());
        assertEquals(245, result.getCarbsG());
    }

    @Test
    void shouldCalculateMacrosForMaintainGoal() {
        MacroResult result = macroCalculator.calculateMacros(2000, Goal.MAINTAIN);

        assertEquals(2000, result.getCalories());
        assertEquals(110, result.getProteinG());
        assertEquals(60, result.getFatG());
        assertEquals(255, result.getCarbsG());
    }

    @Test
    void shouldCalculateMacrosForGainGoal() {
        MacroResult result = macroCalculator.calculateMacros(2000, Goal.GAIN);

        assertEquals(2000, result.getCalories());
        assertEquals(130, result.getProteinG());
        assertEquals(60, result.getFatG());
        assertEquals(235, result.getCarbsG());
    }

    @Test
    void shouldRejectCaloriesBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> macroCalculator.calculateMacros(1100, Goal.LOSE)
        );

        assertEquals("Целевая калорийность должна быть не меньше 1200", exception.getMessage());
    }

    @Test
    void shouldCalculateBoundaryCarbsForMinimumAllowedCalories() {
        MacroResult result = macroCalculator.calculateMacros(1200, Goal.GAIN);

        assertEquals(1200, result.getCalories());
        assertEquals(130, result.getProteinG());
        assertEquals(60, result.getFatG());
        assertEquals(35, result.getCarbsG());
    }
}
