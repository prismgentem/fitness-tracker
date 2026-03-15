package ru.prisma.fitnesstracker.service;

import ru.prisma.fitnesstracker.model.BmiCategory;

public class BmiService {

    private static final double CENTIMETERS_IN_METER = 100.0;
    private static final double UNDERWEIGHT_THRESHOLD = 18.5;
    private static final double NORMAL_THRESHOLD = 25.0;
    private static final double OVERWEIGHT_THRESHOLD = 30.0;

    public double calculateBmi(double heightCm, double weightKg) {
        validateInput(heightCm, weightKg);

        double heightM = heightCm / CENTIMETERS_IN_METER;
        return weightKg / (heightM * heightM);
    }

    public BmiCategory classify(double bmi) {
        if (bmi < UNDERWEIGHT_THRESHOLD) {
            return BmiCategory.UNDERWEIGHT;
        }
        if (bmi < NORMAL_THRESHOLD) {
            return BmiCategory.NORMAL;
        }
        if (bmi < OVERWEIGHT_THRESHOLD) {
            return BmiCategory.OVERWEIGHT;
        }
        return BmiCategory.OBESE;
    }

    private void validateInput(double heightCm, double weightKg) {
        if (heightCm <= 0 || weightKg <= 0) {
            throw new IllegalArgumentException("heightCm and weightKg must be > 0");
        }
    }
}
