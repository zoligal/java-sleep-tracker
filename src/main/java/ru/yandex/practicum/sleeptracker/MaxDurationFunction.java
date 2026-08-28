package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalLong;

public class MaxDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        OptionalLong maximumDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .max();
        Long resultValue = maximumDuration.orElse(0L);
        return new SleepAnalysisResult<>("Максимальная продолжительность сессии (мин):", resultValue);
    }
}
