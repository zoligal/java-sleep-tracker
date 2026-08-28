package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepFileParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static List<SleepingSession> parseFile(String path) throws IOException {
        return Files.lines(Paths.get(path))
                .filter(line -> !line.isBlank())
                .map(SleepFileParser::parseLine)
                .collect(Collectors.toList());
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid line format: " + line);
        }
        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2].trim().toUpperCase());
        return new SleepingSession(start, end, quality);
    }
}
