package logPackage;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LogReader {

    private static final Logger logger = LogManager.getLogger(LogReader.class);

    public static void main(String[] args) {
        String filePath = "src/main/resources/ghidra/Ghidra/Features/Base/src/main/java/ghidra/GhidraRun.java";
        File file = new File(filePath);

        try (FileInputStream fis = new FileInputStream(file)) {
            ParseResult<CompilationUnit> parseResult = new JavaParser().parse(fis);

            if (parseResult.isSuccessful()) {
                CompilationUnit cu = parseResult.getResult().orElse(null);
                if (cu != null) {
                    cu.accept(new LogVisitor(), null);
                }
            } else {
                logger.error("Failed to parse the file: " + filePath);
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + filePath);
        } catch (IOException e) {
            logger.error("Error reading file: " + filePath, e);
        }
    }

    private static class LogVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration md, Void arg) {
            logger.info("Method Declaration: {}", md.getDeclarationAsString(false, false, false));
            super.visit(md, arg);
        }

        @Override
        public void visit(VariableDeclarator vd, Void arg) {
            logger.info("Variable Declaration: {}", vd.getNameAsString());
            super.visit(vd, arg);
        }

        @Override
        public void visit(MethodCallExpr mce, Void arg) {
            logger.info("Method Call: {}", mce);
            super.visit(mce, arg);
        }
    }
}
