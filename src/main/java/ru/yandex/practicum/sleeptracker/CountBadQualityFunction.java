package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class CountBadQualityFunction implements SleepAnalysisFunction {

    @Override
    public SleepAnalysisResult<Long> analyze(List<SleepingSession> sessions) {
        Long badQualitySessionCount = sessions.stream()
                .filter(session -> SleepQuality.BAD == session.getQuality())
                .count();
        return new SleepAnalysisResult<>("Количество сессий с плохим качеством сна: ", badQualitySessionCount);
    }
}
