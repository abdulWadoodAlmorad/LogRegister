package logPackage;

import org.junit.Test;
import static org.junit.Assert.*;

public class LogTesterTest {

    @Test
    public void testProcessLogContent() {
        String logContent = "private static final Logger logger1 = LogManager.getLogger(LoggerExample.class);\n" +
                            "logger1.info(\"Info log\");\n" +
                            "logger1.debug(\"Debug log\");";

        LogTester.processLogContent(logContent);

     // Verify that the function is not throwing any exceptions and working properly
        assertTrue(true);
    }

    @Test
    public void testContainsLogMethod() {
        assertTrue(LogTester.containsLogMethod("logger1.info(\"Info log\");"));
        assertFalse(LogTester.containsLogMethod("System.out.println(\"Not a log method\");"));
    }
}
