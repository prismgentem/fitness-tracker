package ru.prisma.fitnesstracker.repository;

import java.util.Optional;
import ru.prisma.fitnesstracker.model.CalculationResult;

public class StubFailHistoryRepository implements CalculationHistoryRepository {

    @Override
    public void save(CalculationResult result) {
        throw new RuntimeException("DB is down");
    }

    @Override
    public Optional<CalculationResult> findLast() {
        return Optional.empty();
    }
}
