package ru.prisma.fitnesstracker.model;

import java.time.LocalDateTime;

public class CalculationResult {

    private final UserProfile profile;
    private final int targetCalories;
    private final MacroResult macros;
    private final LocalDateTime createdAt;

    public CalculationResult(UserProfile profile, int targetCalories, MacroResult macros, LocalDateTime createdAt) {
        this.profile = profile;
        this.targetCalories = targetCalories;
        this.macros = macros;
        this.createdAt = createdAt;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public int getTargetCalories() {
        return targetCalories;
    }

    public MacroResult getMacros() {
        return macros;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
