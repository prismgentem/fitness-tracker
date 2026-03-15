package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.CalculationResult;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;
import ru.prisma.fitnesstracker.repository.StubSuccessHistoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculationServiceIntegrationTest {

    @Test
    void shouldCalculateAndSaveResult_endToEnd() {
        ProfileValidator validator = new ProfileValidator();
        CalorieCalculator calorieCalculator = new CalorieCalculator(validator);
        MacroCalculator macroCalculator = new MacroCalculator();
        StubSuccessHistoryRepository stubRepo = new StubSuccessHistoryRepository();
        CalculationService calculationService = new CalculationService(
                calorieCalculator,
                macroCalculator,
                stubRepo
        );
        UserProfile profile = new UserProfile(28, 178.0, 72.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        CalculationResult result = calculationService.calculateAndSave(profile);

        assertNotNull(result);
        assertTrue(result.getTargetCalories() >= 1200);
        assertEquals(result.getTargetCalories(), result.getMacros().getCalories());
        assertEquals(110, result.getMacros().getProteinG());
        assertTrue(stubRepo.findLast().isPresent());

        CalculationResult savedResult = stubRepo.findLast().orElseThrow();
        assertEquals(result.getTargetCalories(), savedResult.getTargetCalories());
        assertEquals(result.getMacros().getCalories(), savedResult.getMacros().getCalories());
        assertEquals(result.getMacros().getProteinG(), savedResult.getMacros().getProteinG());
        assertEquals(result.getMacros().getFatG(), savedResult.getMacros().getFatG());
        assertEquals(result.getMacros().getCarbsG(), savedResult.getMacros().getCarbsG());
        assertEquals(result.getProfile().getAge(), savedResult.getProfile().getAge());
        assertEquals(result.getProfile().getHeightCm(), savedResult.getProfile().getHeightCm());
        assertEquals(result.getProfile().getWeightKg(), savedResult.getProfile().getWeightKg());
        assertEquals(result.getProfile().getSex(), savedResult.getProfile().getSex());
        assertEquals(result.getProfile().getActivityLevel(), savedResult.getProfile().getActivityLevel());
        assertEquals(result.getProfile().getGoal(), savedResult.getProfile().getGoal());
    }

    @Test
    void shouldFailOnInvalidProfile_andNotSave() {
        ProfileValidator validator = new ProfileValidator();
        CalorieCalculator calorieCalculator = new CalorieCalculator(validator);
        MacroCalculator macroCalculator = new MacroCalculator();
        StubSuccessHistoryRepository stubRepo = new StubSuccessHistoryRepository();
        CalculationService calculationService = new CalculationService(
                calorieCalculator,
                macroCalculator,
                stubRepo
        );
        UserProfile profile = new UserProfile(11, 178.0, 72.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculationService.calculateAndSave(profile)
        );

        assertEquals("Возраст должен быть от 12 до 80", exception.getMessage());
        assertTrue(stubRepo.findLast().isEmpty());
    }
}
