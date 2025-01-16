package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger {

    private String logFilePath;
    private DateTimeFormatter dateTimeFormatter;

    // Konstruktor przyjmujący ścieżkę do pliku logu
    public FileLogger(String logFilePath) {
        this.logFilePath = logFilePath;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    // Metoda do zapisywania wiadomości do pliku logu
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(dateTimeFormatter);
        String logEntry = String.format("[%s] %s", timestamp, message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać do pliku logu: " + e.getMessage());
        }
    }

}
