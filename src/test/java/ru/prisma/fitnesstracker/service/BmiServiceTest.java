package ru.prisma.fitnesstracker.service;

import org.junit.jupiter.api.Test;
import ru.prisma.fitnesstracker.model.BmiCategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BmiServiceTest {

    private final BmiService bmiService = new BmiService();

    @Test
    void shouldCalculateBmi_correctValue() {
        double bmi = bmiService.calculateBmi(180, 81);

        assertEquals(25.0, bmi, 0.01);
    }

    @Test
    void shouldClassifyBmi_normal() {
        BmiCategory category = bmiService.classify(22.0);

        assertEquals(BmiCategory.NORMAL, category);
    }

    @Test
    void shouldThrowOnInvalidInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bmiService.calculateBmi(0, 70)
        );

        assertEquals("heightCm and weightKg must be > 0", exception.getMessage());
    }
}
