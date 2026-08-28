package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalDouble;

public class AverageDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Double> analyze(List<SleepingSession> sessions) {
        OptionalDouble averageValue = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average();

        double resultValue = averageValue.orElse(0.0);
        return new SleepAnalysisResult<>("Средняя продолжительность сессии (мин):", resultValue);
    }
}
