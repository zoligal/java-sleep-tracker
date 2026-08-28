package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CountBadQualityFunctionTest {

    @Test
    void shouldCountZeroWhenNoBadSessions() {
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
        var function = new CountBadQualityFunction();
        var result = function.analyze(List.of(session1, session2));

        assertEquals("Количество сессий с плохим качеством сна: ", result.getDescription());
        assertEquals(0L, result.getValue());
    }

    @Test
    void shouldCountBadSessionsCorrectly() {
        var session1 = new SleepingSession(
                LocalDateTime.of(2025, 10, 1, 22, 15),
                LocalDateTime.of(2025, 10, 2, 8, 0),
                SleepQuality.BAD
        );
        var session2 = new SleepingSession(
                LocalDateTime.of(2025, 10, 2, 23, 0),
                LocalDateTime.of(2025, 10, 3, 8, 0),
                SleepQuality.GOOD
        );
        var session3 = new SleepingSession(
                LocalDateTime.of(2025, 10, 3, 23, 30),
                LocalDateTime.of(2025, 10, 4, 6, 20),
                SleepQuality.BAD
        );

        var function = new CountBadQualityFunction();
        var result = function.analyze(List.of(session1, session2, session3));

        assertEquals("Количество сессий с плохим качеством сна: ", result.getDescription());
        assertEquals(2L, result.getValue());
    }
}
