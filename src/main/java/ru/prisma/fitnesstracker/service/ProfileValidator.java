package ru.prisma.fitnesstracker.service;

import ru.prisma.fitnesstracker.model.UserProfile;

public class ProfileValidator {

    public void validate(UserProfile profile) {
        if (profile == null) {
            throw new IllegalArgumentException("Profile must not be null");
        }
        if (profile.getAge() == null) {
            throw new IllegalArgumentException("Age must not be null");
        }
        if (profile.getHeightCm() == null) {
            throw new IllegalArgumentException("Height must not be null");
        }
        if (profile.getWeightKg() == null) {
            throw new IllegalArgumentException("Weight must not be null");
        }
        if (profile.getSex() == null) {
            throw new IllegalArgumentException("Sex must not be null");
        }
        if (profile.getActivityLevel() == null) {
            throw new IllegalArgumentException("Activity level must not be null");
        }
        if (profile.getGoal() == null) {
            throw new IllegalArgumentException("Goal must not be null");
        }
        if (profile.getAge() < 12 || profile.getAge() > 80) {
            throw new IllegalArgumentException("Age must be between 12 and 80");
        }
        if (profile.getHeightCm() < 120.0 || profile.getHeightCm() > 220.0) {
            throw new IllegalArgumentException("Height must be between 120 and 220 cm");
        }
        if (profile.getWeightKg() < 30.0 || profile.getWeightKg() > 250.0) {
            throw new IllegalArgumentException("Weight must be between 30 and 250 kg");
        }
    }
}
