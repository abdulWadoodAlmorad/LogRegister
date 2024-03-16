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
        LogTester.processLogContent("");
    }
}
