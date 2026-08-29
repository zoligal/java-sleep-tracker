package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinDurationFunctionTest {
    @Test
    void shouldReturnZeroIfEmpty() {
        var function = new MinDurationFunction();
        var result = function.analyze(List.of());

        assertEquals("Минимальная продолжительность сессии (мин):", result.getDescription());
        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldFindMinimumCorrectly() {
        // Сессия 1: 22:15–08:00 = 585 минут
        var session1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 15),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        );


        var session2 = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 10, 30),
                LocalDateTime.of(2025, 10, 3, 11, 20),
                SleepQuality.NORMAL
        );

        var function = new MinDurationFunction();
        var result = function.analyze(List.of(session1, session2));

        assertEquals("Минимальная продолжительность сессии (мин):", result.getDescription());
        assertEquals(50L, result.getValue());
    }

}
