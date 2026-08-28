package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountSessionsFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        long sessionCount = sessions.stream().count();
        return new SleepAnalysisResult<>("Количество сессий сна: ", sessionCount);
    }
}
