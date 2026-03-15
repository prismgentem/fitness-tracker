package ru.prisma.fitnesstracker.service;

import java.time.LocalDateTime;
import ru.prisma.fitnesstracker.model.CalculationResult;
import ru.prisma.fitnesstracker.model.MacroResult;
import ru.prisma.fitnesstracker.model.UserProfile;
import ru.prisma.fitnesstracker.repository.CalculationHistoryRepository;

public class CalculationService {

    private final CalorieCalculator calorieCalculator;
    private final MacroCalculator macroCalculator;
    private final CalculationHistoryRepository historyRepository;

    public CalculationService(
            CalorieCalculator calorieCalculator,
            MacroCalculator macroCalculator,
            CalculationHistoryRepository historyRepository
    ) {
        this.calorieCalculator = calorieCalculator;
        this.macroCalculator = macroCalculator;
        this.historyRepository = historyRepository;
    }

    public CalculationResult calculateAndSave(UserProfile profile) {
        int targetCalories = calorieCalculator.calculateTargetCalories(profile);
        MacroResult macros = macroCalculator.calculateMacros(targetCalories, profile.getGoal());
        CalculationResult result = new CalculationResult(profile, targetCalories, macros, LocalDateTime.now());

        historyRepository.save(result);
        return result;
    }
}
