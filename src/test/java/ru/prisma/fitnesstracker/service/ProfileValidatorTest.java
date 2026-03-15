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
    void shouldRejectAgeOutOfRange() {
        UserProfile profile = new UserProfile(10, 175.0, 70.0, Sex.M, ActivityLevel.MEDIUM, Goal.MAINTAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Age must be between 12 and 80", exception.getMessage());
    }

    @Test
    void shouldRejectHeightOutOfRange() {
        UserProfile profile = new UserProfile(25, 110.0, 70.0, Sex.F, ActivityLevel.LOW, Goal.LOSE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Height must be between 120 and 220 cm", exception.getMessage());
    }

    @Test
    void shouldRejectWeightOutOfRange() {
        UserProfile profile = new UserProfile(25, 170.0, 20.0, Sex.F, ActivityLevel.HIGH, Goal.GAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Weight must be between 30 and 250 kg", exception.getMessage());
    }

    @Test
    void shouldRejectNullField() {
        UserProfile profile = new UserProfile(25, 170.0, 65.0, null, ActivityLevel.HIGH, Goal.GAIN);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> validator.validate(profile));

        assertEquals("Sex must not be null", exception.getMessage());
    }
}
