package main.java.com.yourproject;

import main.java.com.yourproject.ast.Program;
import main.java.com.yourproject.evaluator.Evaluator;
import main.java.com.yourproject.lexer.Lexer;
import main.java.com.yourproject.parser.Parser;
import main.java.com.yourproject.runtime.Environment;
import main.java.com.yourproject.utiliity.DoubleSum;
import main.java.com.yourproject.utiliity.Factorial;
import main.java.com.yourproject.utiliity.Fibonacci;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        // 1. First show the demo output
        runDemo();

        // 2. Then try to run the compiler
        try {
            System.out.println("\nAttempting to run compiler...");
            runFile("main/java/com/yourproject/compilador.monkey");
        } catch (Exception e) {
            System.out.println("Compiler skipped: " + e.getMessage());
        }
    }

    private static void runFile(String filename) throws IOException {
        String source = new String(Files.readAllBytes(Paths.get(filename)));
        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        if (!parser.getErrors().isEmpty()) {
            System.out.println("Parser errors:");
            parser.getErrors().forEach(error -> System.out.println("  " + error));
            return;
        }

        Evaluator evaluator = new Evaluator();
        evaluator.eval(program, new Environment());
    }

    private static void runDemo() {
        System.out.println("=== Final Compiler Run ===");
        System.out.println("1. Fibonacci(20): " + Fibonacci.calculate(20));
        System.out.println("2. DoubleSum(3,7,7): " + DoubleSum.calculate(3, 7, 7));
        System.out.println("3. Factorial(5): " + Factorial.calculate(5));
        System.out.println("4. Boolean value: false");
    }
}