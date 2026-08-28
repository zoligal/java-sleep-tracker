package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CountSessionsFunctionTest {

    @Test
    void shouldReturnZeroWhenNoSessions() {
        CountSessionsFunction function = new CountSessionsFunction();
        var result = function.analyze(List.of());

        assertEquals("Количество сессий сна: ", result.getDescription());
        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldCountSessionsCurrently() {
        var session1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 15),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.GOOD
        );
        var session2 = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 0),
                LocalDateTime.of(2025, 10, 3, 8, 0),
                SleepQuality.NORMAL
        );
        CountSessionsFunction function = new CountSessionsFunction();
        var result = function.analyze(List.of(session1, session2));

        assertEquals("Количество сессий сна: ", result.getDescription());
        assertEquals(2L, result.getValue());
    }
}
