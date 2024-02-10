package logPackage;

public class MainApplication {

    public static void main(String[] args) {

         LogReader.main(args);

        // Execute LogProcessor to estimate log similarity and modify them
        // Führen Sie LogProcessor aus, um die Ähnlichkeit der Protokolle zu schätzen und diese zu ändern
        LogProcessor.main(args);

        // Set the value of logContent
        // Legen Sie den Wert von logContent fest
//        String logContent = "";

        // Call the processLogContent function from the LogTester class
        // Rufen Sie die Funktion processLogContent aus der Klasse LogTester auf
        LogTester.processLogContent("package obsqra;\r\n"
        		+ "\r\n"
        		+ "import org.apache.logging.log4j.LogManager;\r\n"
        		+ "import org.apache.logging.log4j.Logger;\r\n"
        		+ "\r\n"
        		+ "public class LoggerExample {\r\n"
        		+ "\r\n"
        		+ "    private static final Logger loggerOne = LogManager.getLogger(LoggerExample.class);\r\n"
        		+ "    private static final Logger loggerTwo = LogManager.getLogger(LoggerExample.class);\r\n"
        		+ "    private static final Logger loggerThree = LogManager.getLogger(LoggerExample.class);\r\n"
        		+ "    private static final Logger customLogger = LogManager.getLogger(\"CustomLogger\");\r\n"
        		+ "\r\n"
        		+ "    public static void main(String[] args) {\r\n"
        		+ "        useLoggers();\r\n"
        		+ "        customLogger.debug(\"This is customLogger\");\r\n"
        		+ "    }\r\n"
        		+ "\r\n"
        		+ "    private static void useLoggers() {\r\n"
        		+ "        loggerOne.info(\"Info message from loggerOne\");\r\n"
        		+ "        loggerTwo.error(\"Error message from loggerTwo\");\r\n"
        		+ "        loggerThree.debug(\"Debug message from loggerThree\");\r\n"
        		+ "    }\r\n"
        		+ "}");
    }
}
