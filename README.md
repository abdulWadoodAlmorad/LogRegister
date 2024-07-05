# LogRegister

<!-- <img width="1607" alt="shema" src="https://github.com/abdulWadoodAlmorad/LogRegister/assets/97237663/1f087139-c55a-47c2-ba3c-835960f3ec0f"> -->

### Grundlegende Anforderungen

1. **Java JDK:**
   - Lade und installiere das Java Development Kit (JDK) von der offiziellen Website:
     [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
 
- Java JDK 11 oder höher
- IDE: IntelliJ IDEA, Eclipse oder eine andere kompatible IDE
   - Stelle sicher, dass das JDK korrekt installiert ist, indem du folgenden Befehl in der Kommandozeile ausführst:
     ```bash
     java -version
     ```

2. **Apache Maven:**
   - Lade und installiere Maven von der offiziellen Website:
     [Download Maven](https://maven.apache.org/download.cgi)
     - Maven 3.6 oder höher
   - Stelle sicher, dass Maven korrekt installiert ist, indem du folgenden Befehl in der Kommandozeile ausführst:
     ```bash
     mvn -v
     ```

### Projekt herunterladen

1. **Repository klonen:**
   - Verwende folgenden Befehl, um das Repository auf deinen lokalen Rechner zu klonen:
     ```bash
     git clone https://github.com/abdulWadoodAlmorad/LogRegister.git
     ```
   - Wechsle in das Projektverzeichnis:
     ```bash
     cd LogRegister
     ```

### Projekt einrichten

1. **Abhängigkeiten herunterladen und Projekt erstellen:**
   - Verwende folgenden Befehl, um die Abhängigkeiten herunterzuladen und das Projekt zu erstellen:
     ```bash
     mvn clean install
     ```

### Anwendung ausführen

1. **MainApplication.java ausführen:**
   - Nachdem das Projekt mit Maven erstellt wurde, kannst du die Hauptanwendung von MainApplication.java ausführen.

2. **Erwartete Ergebnisse:**
   - Beim Ausführen der Anwendung solltest du die folgenden Informationen in der Kommandozeile oder in der Benutzeroberfläche der Anwendung (falls vorhanden) sehen:
     - **Anzahl der gelösten Typen:** Eine Anzahl der erfolgreich gelösten Typen.
     - **Anzahl der ungelösten Typen:** Eine Anzahl der Typen, die nicht gelöst werden konnten.

### Hinweis zu ungelösten Typen

**Mögliche Ursachen für ungelöste Typen:**
- Fehlende Bibliotheken oder Abhängigkeiten im Klassenpfad.
- Spezielle Umgebungsabhängigkeiten, die nicht korrekt eingerichtet wurden.

### Testen der Anwendung

1. **JUnit Tests ausführen:**
   - Das Projekt enthält zwei Testklassen zur Überprüfung der Funktionalität: `LogReaderTest` und `LogTesterTest`. Diese befinden sich im Verzeichnis `src/test/java/logPackage`.
   - Verwende folgenden Befehl, um die Tests mit Maven auszuführen:
     ```bash
     mvn test
     ```
   - Stelle sicher, dass alle Tests erfolgreich ausgeführt werden und keine Fehler auftreten.
   
2. **Erwartete Testergebnisse:**
   - Die Tests sollten die Funktionalität der Log-Leser- und -Tester-Klassen überprüfen und sicherstellen, dass die erwarteten Ausgaben und Verhalten korrekt sind.
   - Überprüfe die Konsolenausgabe oder die generierten Testberichte, um sicherzustellen, dass alle Tests bestanden wurden.
