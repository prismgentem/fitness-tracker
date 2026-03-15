package ru.prisma.fitnesstracker.model;

import ru.prisma.fitnesstracker.model.enums.ActivityLevel;
import ru.prisma.fitnesstracker.model.enums.Goal;
import ru.prisma.fitnesstracker.model.enums.Sex;

public class UserProfile {

    private final Integer age;
    private final Double heightCm;
    private final Double weightKg;
    private final Sex sex;
    private final ActivityLevel activityLevel;
    private final Goal goal;

    public UserProfile(Integer age, Double heightCm, Double weightKg, Sex sex, ActivityLevel activityLevel, Goal goal) {
        this.age = age;
        this.heightCm = heightCm;
        this.weightKg = weightKg;
        this.sex = sex;
        this.activityLevel = activityLevel;
        this.goal = goal;
    }

    public Integer getAge() {
        return age;
    }

    public Double getHeightCm() {
        return heightCm;
    }

    public Double getWeightKg() {
        return weightKg;
    }

    public Sex getSex() {
        return sex;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public Goal getGoal() {
        return goal;
    }
}
