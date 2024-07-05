package logPackage;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LogReader {

    private static final Logger logger = LogManager.getLogger(LogReader.class);

    public static void main(String[] args) {
        String projectPath = "src/main/resources/ghidra/Ghidra/Features/Base/src/main/java/ghidra";
        File projectDir = new File(projectPath);

     // Initialize JavaParser to use JavaSymbolSolver
        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setSymbolResolver(new JavaSymbolSolver(new ReflectionTypeSolver()));

        JavaParser javaParser = new JavaParser(parserConfiguration);

     // Analyze all project files
        try {
            for (File file : projectDir.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    parseFile(javaParser, file);
                }
            }
        } catch (Exception e) {
            logger.error("Error parsing project files", e);
        }
    }

    private static void parseFile(JavaParser javaParser, File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            ParseResult<CompilationUnit> parseResult = javaParser.parse(fis);

            if (parseResult.isSuccessful()) {
                CompilationUnit cu = parseResult.getResult().orElse(null);
                if (cu != null) {
                    cu.accept(new LogVisitor(), null);
                }
            } else {
                logger.error("Failed to parse the file: {}", file.getAbsolutePath());
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", file.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Error reading file: {}", file.getAbsolutePath(), e);
        }
    }

    private static class LogVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            logger.info("Method Declaration: {}", md.getDeclarationAsString(false, false, false));
            md.accept(new MethodContentVisitor(), null);
            super.visit(md, arg);
        }

        @Override
        public void visit(VariableDeclarator vd, Void arg) {
            if (vd.getType().asString().contains("Logger") || 
                (vd.getInitializer().isPresent() && vd.getInitializer().get().toString().contains("LogManager"))) {
                logger.debug("Logger Variable Declaration: {}", vd.getNameAsString());
            }
            super.visit(vd, arg);
        }

        @Override
        public void visit(MethodCallExpr mce, Void arg) {
            logger.trace("Method Call: {}", mce);
            super.visit(mce, arg);
        }
    }

    private static class MethodContentVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            logger.info("Method Content: {}", md.getBody().orElse(null));
            // Check for method's body content
            if (md.getBody().isPresent()) {
                md.getBody().get().accept(new StatementVisitor(), arg);
            }
            super.visit(md, arg);
        }
    }

    private static class StatementVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodCallExpr mce, Void arg) {
        	//Keep the old function
            logger.trace("Method Call inside Method: {}", mce);
            
         // Calculate the number of types for arguments
            Set<String> argumentTypes = new HashSet<>();
            mce.getArguments().forEach(argExpr -> {
                try {
                    String argType = resolveArgumentType(argExpr);
                    argumentTypes.add(argType);
                } catch (Exception e) {
                    logger.error("Error resolving argument type for: {}", argExpr, e);
                    argumentTypes.add("Unresolved");
                }
            });

            logger.trace("Unique argument types: {}", argumentTypes);
            logger.trace("Number of unique argument types: {}", argumentTypes.size());

            super.visit(mce, arg);
        }

        private String resolveArgumentType(Expression argExpr) {
            try {
                if (argExpr.isLambdaExpr()) {
                    return "Lambda";
                } else if (argExpr.isMethodCallExpr()) {
                    return "MethodCall";
                } else {
                    return argExpr.calculateResolvedType().describe();
                }
            } catch (Exception e) {
                logger.error("Error resolving argument type for: {}", argExpr, e);
                return "Unresolved";
            }
        }
    }

}
