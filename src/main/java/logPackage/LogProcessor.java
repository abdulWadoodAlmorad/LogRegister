package logPackage;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.Statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class LogProcessor {

    public static void main(String[] args) {
        // Step 1: Read the input log dataset
        // Schritt 1: Lesen des Eingabe-Log-Datensatzes
        File inputFile = new File("src/main/resources/inputLogDataset.txt");
        CompilationUnit compilationUnit;

        try {
            compilationUnit = StaticJavaParser.parse(inputFile);

            // FR1: Estimate log heterogeneity
            // FR1: Schätzen der Log-Heterogenität
            double heterogeneity = estimateHeterogeneity(compilationUnit);
            System.out.println("Estimated Log Heterogeneity: " + heterogeneity);
            System.out.println("Geschätzte Log-Heterogenität: " + heterogeneity);

            // FR2: Create logs with industry-like characteristics
            // FR2: Erstellen von Protokollen mit branchenspezifischen Merkmalen
//            createIndustryLikeLogs(compilationUnit);

            // FR3: Count number of unique characters
            // FR3: Zählen der Anzahl eindeutiger Zeichen
            Set<Character> uniqueCharacters = findUniqueCharacters(compilationUnit);
            System.out.println("Number of unique characters: " + uniqueCharacters.size());
            System.out.println("Anzahl eindeutiger Zeichen: " + uniqueCharacters.size());

            // FR4: Count number of unique lines of different length
            // FR4: Zählen der Anzahl eindeutiger Zeilen unterschiedlicher Länge
            Set<String> uniqueLines = findUniqueLines(compilationUnit);
            System.out.println("Number of unique lines: " + uniqueLines.size());
            System.out.println("Anzahl eindeutiger Zeilen: " + uniqueLines.size());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static double estimateHeterogeneity(CompilationUnit compilationUnit) {
        List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);

        Map<String, Integer> keywordCounts = methods.stream()
                .map(MethodDeclaration::getBody)
                .filter(body -> body.isPresent())
                .flatMap(body -> body.get().getStatements().stream())
                .filter(Statement::isExpressionStmt)
                .map(Statement::toString)
                .flatMap(s -> List.of(s.split("\\s+")).stream())
                .collect(Collectors.toMap(String::toLowerCase, w -> 1, Integer::sum));

        int totalKeywords = keywordCounts.values().stream().mapToInt(Integer::intValue).sum();
        double similarity = (double) keywordCounts.getOrDefault("logger", 0) / totalKeywords;

        System.out.println("Total Keywords: " + totalKeywords);
        System.out.println("Keyword counts:");
        keywordCounts.forEach((key, value) -> System.out.println("- " + key + ": " + value + " times"));

        return similarity;
    }

    private static Set<Character> findUniqueCharacters(CompilationUnit compilationUnit) {
        String code = compilationUnit.toString();
        Set<Character> uniqueCharacters = new HashSet<>();

        for (char character : code.toCharArray()) {
            if (!Character.isWhitespace(character)) {
                uniqueCharacters.add(character);
            }
        }

        return uniqueCharacters;
    }

    private static Set<String> findUniqueLines(CompilationUnit compilationUnit) {
        List<String> lines;
        try {
            lines = Files.readAllLines(compilationUnit.getStorage().get().getPath());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashSet<>();
        }

        return new HashSet<>(lines);
    }
}
