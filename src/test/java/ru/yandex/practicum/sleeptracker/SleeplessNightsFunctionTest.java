package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class SleeplessNightsFunctionTest {

    private final SleepAnalysisFunction function = new SleeplessNightsFunction();

    @Test
    void shouldCountOneWhenNoNightSleep() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 14, 0),
                LocalDateTime.of(2025, 10, 1, 18, 0),
                SleepQuality.NORMAL
        );
        var result = function.analyze(List.of(session));

        assertEquals("Количество бессонных ночей:", result.getDescription());
        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldCountCorrectlyWithMultipleNights() {
        var session1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 0),
                LocalDateTime.of(2025, 10, 2, 6, 30),
                SleepQuality.GOOD
        );
        var session2 = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 12, 0),
                LocalDateTime.of(2025, 10, 3, 16, 0),
                SleepQuality.BAD
        );

        var result = function.analyze(List.of(session1, session2));

        assertEquals("Количество бессонных ночей:", result.getDescription());
        assertEquals(2L, result.getValue());
    }

    @Test
    void shouldTreatBoundaryCorrectly() {
        var session = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 6, 0),
                LocalDateTime.of(2025, 10, 1, 9, 0),
                SleepQuality.BAD
        );
        var result = function.analyze(List.of(session));

        assertEquals("Количество бессонных ночей:", result.getDescription());
        assertEquals(1L, result.getValue());
    }
}
