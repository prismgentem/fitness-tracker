package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.CalculationResult;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;
import ru.prisma.fitnesstracker.repository.StubFailHistoryRepository;
import ru.prisma.fitnesstracker.repository.StubSuccessHistoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CalculationServiceTest {

    private final CalorieCalculator calorieCalculator = new CalorieCalculator();
    private final MacroCalculator macroCalculator = new MacroCalculator();

    @Test
    void shouldRejectInvalidProfileAndNotSave_blackBox() {
        StubSuccessHistoryRepository repository = new StubSuccessHistoryRepository();
        CalculationService service = new CalculationService(calorieCalculator, macroCalculator, repository);
        UserProfile profile = new UserProfile(11, 170.0, 65.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.calculateAndSave(profile)
        );

        assertEquals("Возраст должен быть от 12 до 80", exception.getMessage());
        assertEquals(0, repository.getSaveCallsCount());
    }

    @Test
    void shouldPropagateRepositoryFailureAfterCalculations() {
        CalculationService service = new CalculationService(
                calorieCalculator,
                macroCalculator,
                new StubFailHistoryRepository()
        );
        UserProfile profile = new UserProfile(28, 178.0, 74.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.calculateAndSave(profile));

        assertEquals("DB is down", exception.getMessage());
    }

    @Test
    void shouldCalculateAndSaveWithSuccessStub() {
        StubSuccessHistoryRepository repository = new StubSuccessHistoryRepository();
        CalculationService service = new CalculationService(calorieCalculator, macroCalculator, repository);
        UserProfile profile = new UserProfile(28, 178.0, 74.0, Sex.M, ActivityLevel.MEDIUM, Goal.GAIN);

        CalculationResult result = service.calculateAndSave(profile);

        assertTrue(result.getTargetCalories() >= 1200);
        assertSame(result, repository.findLast().orElseThrow());
        assertEquals(1, repository.getSaveCallsCount());
    }
}
