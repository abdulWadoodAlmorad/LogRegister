package logPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReader {

    private static final Logger logger = LogManager.getLogger(LogReader.class);

    public static void main(String[] args) {
        String filePath = "src/main/resources/inputLogDataset.txt";

        List<String> loggerNames = new ArrayList<>();

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            String line;
            while ((line = file.readLine()) != null) {
                // Search for logger declarations in each line
                // Suche nach Logger-Deklarationen in jeder Zeile
                Matcher matcher = Pattern.compile("\\b(Logger\\s+(\\w+)\\s*=\\s*LogManager\\.getLogger\\(.*?\\);)").matcher(line);
                while (matcher.find()) {
                    String loggerDeclaration = matcher.group(1);
                    String loggerName = matcher.group(2);

                    // Log found logger declarations
                    // Protokollieren Sie gefundene Logger-Deklarationen
                    logger.info("Found Logger: {}", loggerDeclaration);
                    loggerNames.add(loggerName);
                }
            }

            for (String loggerName : loggerNames) {
                findUsageLines(loggerName, file);
            }

        } catch (IOException e) {
            // Log error if file reading fails
            // Protokollieren Sie einen Fehler, wenn das Lesen der Datei fehlschlägt
            logger.error("Error reading log file", e);
        }
    }

    private static void findUsageLines(String className, RandomAccessFile file) throws IOException {
        // Reset file pointer to the beginning of the file
        // Setzen Sie den Dateizeiger auf den Anfang der Datei zurück
        file.seek(0);

        String line;
        int lineNumber = 1;
        while ((line = file.readLine()) != null) {
            // Check if the line contains the logger name and a log method call
            // Überprüfen Sie, ob die Zeile den Namen des Loggers und einen Aufruf der Protokollmethode enthält
            if (line.contains(className) && containsLogMethod(line)) {
                // Log the usage of the logger in the line
                // Protokollieren Sie die Verwendung des Loggers in der Zeile
                logger.info("Logger {} used in line {}: {}", className, lineNumber, line);
            }
            lineNumber++;
        }
    }

    private static boolean containsLogMethod(String line) {
        // Define log methods to search for
        // Definieren Sie Protokollmethoden, nach denen gesucht werden soll
        String[] logMethods = {"debug", "info", "notice", "warning", "error", "critical", "alert", "emergency"};
        for (String method : logMethods) {
            // Check if the line contains any of the log methods
            // Überprüfen Sie, ob die Zeile eine der Protokollmethoden enthält
            if (line.contains("." + method + "(")) {
                return true;
            }
        }
        return false;
    }
}
