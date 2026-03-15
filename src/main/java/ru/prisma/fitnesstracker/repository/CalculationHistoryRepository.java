package ru.prisma.fitnesstracker.repository;

import java.util.Optional;
import ru.prisma.fitnesstracker.model.CalculationResult;

public interface CalculationHistoryRepository {

    void save(CalculationResult result);

    Optional<CalculationResult> findLast();
}
