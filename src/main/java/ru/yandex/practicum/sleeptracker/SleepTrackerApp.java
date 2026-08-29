package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SleepTrackerApp {

    public final List<SleepAnalysisFunction> analysisFunctions;

    public SleepTrackerApp() {
        analysisFunctions = new ArrayList<>();
        analysisFunctions.add(new CountSessionsFunction());
        analysisFunctions.add(new MinDurationFunction());
        analysisFunctions.add(new MaxDurationFunction());
        analysisFunctions.add(new AverageDurationFunction());
        analysisFunctions.add(new CountBadQualityFunction());
        analysisFunctions.add(new SleeplessNightsFunction());
        analysisFunctions.add(new ChronotypeFunction());
    }

    public void run(String filePath) throws IOException {
        List<SleepingSession> sessions = SleepFileParser.parseFile(filePath);

        analysisFunctions.stream()
                .map(function -> function.analyze(sessions))
                .forEach(result -> System.out.println(result.getDescription() +  result.getValue()));
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Ошибка: необходимо передать путь к файлу лога сна как аргумент.");
            System.err.println("Пример запуска: java -cp out/production/java ru.yandex.practicum.sleeptracker.SleepTrackerApp ./test.txt");
            return;
        }

        String filePath = args[0];
        SleepTrackerApp app = new SleepTrackerApp();

        try {
            app.run(filePath);
        } catch (IOException error) {
            System.err.println("Ошибка чтения файла: " + error.getMessage());
            error.printStackTrace();
        }
    }
}
