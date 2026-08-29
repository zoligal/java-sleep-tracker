package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.OptionalLong;

public class MinDurationFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        OptionalLong minimumDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min();

        long resultValue = minimumDuration.orElse(0L);
        return new SleepAnalysisResult<>("Минимальная продолжительность сессии (мин):", resultValue);
    }
}
