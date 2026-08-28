package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MaxDurationFunctionTest {
    @Test
    void shouldReturnWhenZeroNoSessions() {
        var function = new MaxDurationFunction();
        var result = function.analyze(List.of());

        assertEquals("Максимальная продолжительность сессии (мин):", result.getDescription());
        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldFindMaximumCorrectly() {
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
        var function = new MaxDurationFunction();
        var result = function.analyze(List.of(session1, session2));

        assertEquals("Максимальная продолжительность сессии (мин):", result.getDescription()); // <-- исправлено
        assertEquals(585L, result.getValue());
    }
}
