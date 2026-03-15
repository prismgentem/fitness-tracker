package ru.prisma.fitnesstracker.service;

import ru.prisma.fitnesstracker.model.UserProfile;

public class ProfileValidator {

    public void validate(UserProfile profile) {
        if (profile == null
                || profile.getAge() == null
                || profile.getHeightCm() == null
                || profile.getWeightKg() == null
                || profile.getSex() == null
                || profile.getActivityLevel() == null
                || profile.getGoal() == null) {
            throw new IllegalArgumentException("Заполните все обязательные поля");
        }
        if (profile.getAge() < 12 || profile.getAge() > 80) {
            throw new IllegalArgumentException("Возраст должен быть от 12 до 80");
        }
        if (profile.getHeightCm() < 120.0 || profile.getHeightCm() > 220.0) {
            throw new IllegalArgumentException("Рост должен быть от 120 до 220 см");
        }
        if (profile.getWeightKg() < 30.0 || profile.getWeightKg() > 250.0) {
            throw new IllegalArgumentException("Вес должен быть от 30 до 250 кг");
        }
    }
}
