package logPackage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogTester {

    private static final Logger logger = LogManager.getLogger(LogTester.class);

    public static void main(String[] args) {
        // Set the value of logContent
        // Legen Sie den Wert von logContent fest
        String logContent = "    private static final Logger logger1 = LogManager.getLogger(LoggerExample.class);\r\n";

        // Call the processLogContent function
        // Rufen Sie die Funktion processLogContent auf
        processLogContent(logContent);
    }

    public static void processLogContent(String logContent) {
        List<String> loggerNames = new ArrayList<>();

        Matcher loggerDeclarationMatcher = Pattern.compile("\\b(Logger\\s+(\\w+)\\s*=\\s*LogManager\\.getLogger\\(.*?\\);)").matcher(logContent);
        while (loggerDeclarationMatcher.find()) {
            String loggerDeclaration = loggerDeclarationMatcher.group(1);
            String loggerName = loggerDeclarationMatcher.group(2);

            logger.info("Found Logger: {}", loggerDeclaration);
            loggerNames.add(loggerName);
        }

        for (String loggerName : loggerNames) {
            findUsageLines(loggerName, logContent);
        }
    }

    private static void findUsageLines(String className, String logContent) {
        String[] lines = logContent.split("\n");

        int lineNumber = 1;
        for (String line : lines) {
            if (line.contains(className) && containsLogMethod(line)) {
                logger.info("Logger {} used in line {}: {}", className, lineNumber, line);
            }
            lineNumber++;
        }
    }

    private static boolean containsLogMethod(String line) {
        String[] logMethods = {"debug", "info", "notice", "warning", "error", "critical", "alert", "emergency"};
        for (String method : logMethods) {
            if (line.contains("." + method + "(")) {
                return true;
            }
        }
        return false;
    }
}
