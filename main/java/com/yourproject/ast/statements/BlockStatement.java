package main.java.com.yourproject.ast.statements;


import main.java.com.yourproject.tokens.Token;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a block of statements enclosed in curly braces {}.
 * Used in control structures like if/else, while loops, and functions.
 */
public class BlockStatement implements Statement {
    private final Token token; // The { token
    private final List<Statement> statements;

    /**
     * Constructs a new block statement
     * @param token The opening brace token
     */
    public BlockStatement(Token token) {
        this.token = token;
        this.statements = new ArrayList<>();
    }

    /**
     * Adds a statement to the block
     * @param statement The statement to add
     */
    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    /**
     * @return The list of statements in this block
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * @return The token literal of the opening brace
     */
    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    /**
     * @return The string representation of all statements in the block
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{ ");
        for (Statement stmt : statements) {
            builder.append(stmt.toString());
            builder.append(" ");
        }
        builder.append("}");
        return builder.toString();
    }

    /**
     * @return The token associated with this block
     */
    public Token getToken() {
        return token;
    }
}
