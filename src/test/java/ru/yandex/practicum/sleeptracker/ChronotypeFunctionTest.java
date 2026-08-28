package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class ChronotypeFunctionTest {

    private final SleepAnalysisFunction function = new ChronotypeFunction();

    @Test
    void shouldDetectOwl() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 10, 0),
                SleepQuality.GOOD // <-- ДОБАВИЛИ
        );
        var result = function.analyze(List.of(session));

        assertEquals("Хронотип пользователя:", result.getDescription());
        assertEquals(ChronotypeFunction.Chronotype.OWL, result.getValue());
    }

    @Test
    void shouldDetectLark() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 21, 0),
                LocalDateTime.of(2025, 10, 2, 6, 30), // 6:30 следующего дня
                SleepQuality.GOOD
        );
        var result = function.analyze(List.of(session));

        assertEquals("Хронотип пользователя:", result.getDescription());
        assertEquals(ChronotypeFunction.Chronotype.LARK, result.getValue());
    }

    @Test
    void shouldDetectDove() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 30),
                LocalDateTime.of(2025, 10, 2, 8, 0), // 8:00 следующего дня
                SleepQuality.GOOD
        );
        var result = function.analyze(List.of(session));

        assertEquals("Хронотип пользователя:", result.getDescription());
        assertEquals(ChronotypeFunction.Chronotype.DOVE, result.getValue());
    }

    @Test
    void shouldIgnoreDaytimeSessionAndReturnDove() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 13, 0),
                LocalDateTime.of(2025, 10, 1, 17, 0),
                SleepQuality.BAD
        );
        var result = function.analyze(List.of(session));

        assertEquals("Хронотип пользователя:", result.getDescription());
        assertEquals(ChronotypeFunction.Chronotype.DOVE, result.getValue());
    }

    @Test
    void shouldReturnDoveOnTie() {
        var owlSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 23, 30),
                LocalDateTime.of(2025, 10, 2, 10, 0),
                SleepQuality.GOOD
        );

        // Важно: эта сессия тоже должна быть корректной: 21:00 -> 6:30 СЛЕДУЮЩЕГО дня
        var larkSession = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 21, 0),
                LocalDateTime.of(2025, 10, 3, 6, 30),
                SleepQuality.GOOD
        );

        var result = function.analyze(List.of(owlSession, larkSession));

        assertEquals("Хронотип пользователя:", result.getDescription());
        assertEquals(ChronotypeFunction.Chronotype.DOVE, result.getValue());
    }
}
