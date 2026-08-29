package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public class SleeplessNightsFunction implements SleepAnalysisFunction {

    // Контракт: null/пустой список -> 0. Бессонная ночь = нет сна в окне 00:00–06:00

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        if (sessions == null || sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей:", 0L);
        }

        LocalDate minStartDate = sessions.stream()
                .map(session -> session.getStart().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate maxEndDate = sessions.stream()
                .map(session -> session.getEnd().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow();

        long totalDays = java.time.temporal.ChronoUnit.DAYS.between(minStartDate, maxEndDate) + 1;

        long sleeplessCount = Stream.iterate(minStartDate, currentDate -> currentDate.plusDays(1))
                .limit(totalDays)
                .filter(currentDate -> {
                    LocalDateTime nightStart = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);
                    LocalDateTime nightEnd = LocalDateTime.of(currentDate, LocalTime.of(6, 0));

                    boolean hasSleepInNightWindow = sessions.stream().anyMatch(session ->
                            session.getStart().isBefore(nightEnd) &&
                                    session.getEnd().isAfter(nightStart)
                    );

                    return !hasSleepInNightWindow;
                })
                .count();

        return new SleepAnalysisResult<>("Количество бессонных ночей:", sleeplessCount);
    }
}
