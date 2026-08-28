package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChronotypeFunction implements SleepAnalysisFunction {

    public enum Chronotype {
        OWL,
        LARK,
        DOVE
    }

    @Override
    public SleepAnalysisResult<Chronotype> analyze(List<SleepingSession> sessions) {
        Map<Chronotype, Long> counts = new HashMap<>();
        counts.put(Chronotype.OWL, 0L);
        counts.put(Chronotype.LARK, 0L);
        counts.put(Chronotype.DOVE, 0L);

        sessions.stream()
                .filter(session -> hasNightOverlap(session))
                .forEach(session -> {
                    LocalTime startTime = session.getStart().toLocalTime();
                    LocalTime endTime = session.getEnd().toLocalTime();

                    if (startTime.isAfter(LocalTime.of(23, 0)) && endTime.isAfter(LocalTime.of(9, 0))) {
                        counts.merge(Chronotype.OWL, 1L, Long::sum);
                    } else if (startTime.isBefore(LocalTime.of(22, 0)) && endTime.isBefore(LocalTime.of(7, 0))) {
                        counts.merge(Chronotype.LARK, 1L, Long::sum);
                    } else {
                        counts.merge(Chronotype.DOVE, 1L, Long::sum);
                    }
                });

        Chronotype result = getDominantChronotype(counts);
        return new SleepAnalysisResult<>("Хронотип пользователя:",  result);
    }

    private boolean hasNightOverlap(SleepingSession session) {
        LocalDateTime midnight = session.getStart().toLocalDate().atStartOfDay();
        LocalDateTime sixAM = midnight.plusHours(6);

        boolean overlapsToday = session.getStart().isBefore(sixAM) && session.getEnd().isAfter(midnight);
        boolean overlapsNext = session.getStart().isBefore(sixAM.plusDays(1)) && session.getEnd().isAfter(midnight.plusDays(1));
        return overlapsToday || overlapsNext;
    }

    private Chronotype getDominantChronotype(Map<Chronotype, Long> counts) {
        long owlCount = counts.get(Chronotype.OWL);
        long larkCount = counts.get(Chronotype.LARK);
        long doveCount = counts.get(Chronotype.DOVE);

        if (owlCount > larkCount && owlCount > doveCount) {
            return Chronotype.OWL;
        }
        if (larkCount > owlCount && larkCount > doveCount) {
            return Chronotype.LARK;
        }

        return Chronotype.DOVE;
    }
}
