package main.java.com.yourproject.repl;

import main.java.com.yourproject.ast.Program;
import main.java.com.yourproject.evaluator.Evaluator;
import main.java.com.yourproject.lexer.Lexer;
import main.java.com.yourproject.parser.Parser;
import main.java.com.yourproject.runtime.Environment;
import java.util.Scanner;
import java.util.List;

public class Repl {
    private static final String MONKEY_FACE =
            "  .--.  .-\"     \"-.  .--.\n" +
                    " / .. \\/  .-. .-.  \\/ .. \\\n" +
                    "| |  '|  /   Y   \\  |'  | |\n" +
                    "| \\   \\  \\ 0 | 0 /  /   / |\n" +
                    " \\ '- ,\\.-\"`` ``\"-./, -' /\n" +
                    "  `'-' /_   ^ ^   _\\ '-'`\n" +
                    "     .-'|  \\._   _./  |'-.\n" +
                    "    /   `\\   \\ `~` /   /`   \\\n" +
                    "   /      '._ '---' _.'      \\\n" +
                    "  /          '~---~'          \\\n";

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        Environment env = new Environment();

        System.out.println("Welcome to Isaac Cancele's Monkey REPL");
        System.out.println("Type 'exit' to exit\n");
        System.out.println(MONKEY_FACE);

        while (true) {
            System.out.print(">> ");
            String source = scanner.nextLine().trim();

            if (source.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                Lexer lexer = new Lexer(source);
                Parser parser = new Parser(lexer);
                Program program = parser.parseProgram();

                // Show parsing errors if any
                if (!parser.getErrors().isEmpty()) {
                    System.out.println("Parser errors:");
                    for (String error : parser.getErrors()) {
                        System.out.println("  ✖ " + error);
                    }
                    continue;  // Don't evaluate if there are errors
                }

                // Optional: print parsed AST
                // System.out.println("AST:");
                // System.out.println(program);

                Evaluator evaluator = new Evaluator();
                Object result = evaluator.eval(program, env);

                if (result != null) {
                    System.out.println(result.toString());
                }
            } catch (Exception e) {
                System.out.println("Runtime Error: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Goodbye!");
    }
}