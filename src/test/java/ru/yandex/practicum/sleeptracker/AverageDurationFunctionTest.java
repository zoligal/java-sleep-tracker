package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AverageDurationFunctionTest {

    @Test
    void shouldReturnZeroWhenNoSessions() {
        var function = new AverageDurationFunction();
        var result = function.analyze(List.of());

        assertEquals("Средняя продолжительность сессии (мин):", result.getDescription());
        assertEquals(0.0, result.getValue(), 0.001);
    }

    @Test
    void shouldCalculateAverageCorrectly() {
        var session1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 15),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        );
        var session2 = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 14, 30),
                LocalDateTime.of(2025, 10, 3, 15, 20),
                SleepQuality.NORMAL
        );

        var function = new AverageDurationFunction();
        var result = function.analyze(List.of(session1, session2));

        assertEquals("Средняя продолжительность сессии (мин):", result.getDescription());
        assertEquals((585.0 + 50) / 2.0, result.getValue(), 0.001);
    }
}
