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
    void shouldCalculateMacrosForMaintainGoal() {
        MacroResult result = macroCalculator.calculateMacros(2000, Goal.MAINTAIN);

        assertEquals(2000, result.getCalories());
        assertEquals(110, result.getProteinG());
        assertEquals(60, result.getFatG());
        assertTrue(result.getCarbsG() > 0);
    }

    @Test
    void shouldRejectCaloriesBelowMinimum() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> macroCalculator.calculateMacros(1100, Goal.LOSE)
        );

        assertEquals("Целевая калорийность должна быть не меньше 1200", exception.getMessage());
    }
}
