package ru.prisma.fitnesstracker.repository;

import java.util.Optional;
import ru.prisma.fitnesstracker.model.CalculationResult;

public class StubSuccessHistoryRepository implements CalculationHistoryRepository {

    private CalculationResult lastResult;
    private int saveCallsCount;

    @Override
    public void save(CalculationResult result) {
        lastResult = result;
        saveCallsCount++;
    }

    @Override
    public Optional<CalculationResult> findLast() {
        return Optional.ofNullable(lastResult);
    }

    public int getSaveCallsCount() {
        return saveCallsCount;
    }
}
