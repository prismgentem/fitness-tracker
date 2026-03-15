package ru.prisma.fitnesstracker.model;

public class MacroResult {

    private final int calories;
    private final int proteinG;
    private final int fatG;
    private final int carbsG;

    public MacroResult(int calories, int proteinG, int fatG, int carbsG) {
        this.calories = calories;
        this.proteinG = proteinG;
        this.fatG = fatG;
        this.carbsG = carbsG;
    }

    public int getCalories() {
        return calories;
    }

    public int getProteinG() {
        return proteinG;
    }

    public int getFatG() {
        return fatG;
    }

    public int getCarbsG() {
        return carbsG;
    }
}
