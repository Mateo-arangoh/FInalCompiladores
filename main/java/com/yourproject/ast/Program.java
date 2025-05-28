package main.java.com.yourproject.ast;

import main.java.com.yourproject.ast.statements.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the root node of the Monkey language AST.
 * Contains a series of statements that make up the program.
 */
public class Program implements Node {
    private final List<Statement> statements = new ArrayList<>();

    /**
     * @return The list of statements in the program
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * @return The token literal of the first statement, or empty string if no statements
     */
    @Override
    public String tokenLiteral() {
        return statements.isEmpty() ? "" : statements.get(0).tokenLiteral();
    }

    /**
     * @return The string representation of the entire program
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Statement stmt : statements) {
            builder.append(stmt.toString());
        }
        return builder.toString();
    }

    /**
     * Adds a statement to the program
     * @param statement The statement to add
     */
    public void addStatement(Statement statement) {
        statements.add(statement);
    }
}
