package logPackage;

public class MainApplication {

    public static void main(String[] args) {

         LogReader.main(args);

        // Execute LogProcessor to estimate log similarity and modify them
        // Führen Sie LogProcessor aus, um die Ähnlichkeit der Protokolle zu schätzen und diese zu ändern
        LogProcessor.main(args);

        // Rufen Sie die Funktion processLogContent aus der Klasse LogTester auf
        LogTester.processLogContent("");
    }
}
