package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProfileValidatorTest {

    private final ProfileValidator validator = new ProfileValidator();

    @Test
    void shouldAcceptValidProfile() {
        UserProfile profile = new UserProfile(25, 175.0, 70.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        assertDoesNotThrow(() -> validator.validate(profile));
    }

    @Test
    void shouldRejectProfileWithNullRequiredField() {
        UserProfile profile = new UserProfile(25, 175.0, 70.0, null, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Заполните все обязательные поля", exception.getMessage());
    }

    @Test
    void shouldRejectAgeBelowRange() {
        UserProfile profile = new UserProfile(11, 175.0, 70.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Возраст должен быть от 12 до 80", exception.getMessage());
    }

    @Test
    void shouldRejectAgeAboveRange() {
        UserProfile profile = new UserProfile(81, 175.0, 70.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Возраст должен быть от 12 до 80", exception.getMessage());
    }

    @Test
    void shouldRejectHeightBelowRange() {
        UserProfile profile = new UserProfile(25, 119.0, 70.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Рост должен быть от 120 до 220 см", exception.getMessage());
    }

    @Test
    void shouldRejectHeightAboveRange() {
        UserProfile profile = new UserProfile(25, 221.0, 70.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Рост должен быть от 120 до 220 см", exception.getMessage());
    }

    @Test
    void shouldRejectWeightBelowRange() {
        UserProfile profile = new UserProfile(25, 170.0, 29.0, Sex.F, ActivityLevel.HIGH, Goal.GAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Вес должен быть от 30 до 250 кг", exception.getMessage());
    }

    @Test
    void shouldRejectWeightAboveRange() {
        UserProfile profile = new UserProfile(25, 170.0, 251.0, Sex.F, ActivityLevel.HIGH, Goal.GAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Вес должен быть от 30 до 250 кг", exception.getMessage());
    }
}
