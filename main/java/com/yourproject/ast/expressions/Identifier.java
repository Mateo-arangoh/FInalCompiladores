package main.java.com.yourproject.ast.expressions;

import main.java.com.yourproject.tokens.Token;

/**
 * Represents an identifier (variable name) in the Monkey language.
 * Example: In `let x = 5;`, "x" is an Identifier.
 */
public class Identifier implements Expression {
    private final Token token;  // The IDENT token
    private final String value; // The actual identifier name

    /**
     * Constructs a new Identifier node
     * @param token The IDENT token containing the identifier
     * @param value The name of the identifier
     */
    public Identifier(Token token, String value) {
        this.token = token;
        this.value = value;
    }

    /**
     * @return The identifier's name (e.g., "x", "foo", "bar")
     */
    public String getValue() {
        return value;
    }

    /**
     * @return The token's literal representation
     */
    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    /**
     * @return The string representation of the identifier
     */
    @Override
    public String toString() {
        return value;
    }

    // Optional: Useful for testing and debugging
    /**
     * @return The token associated with this identifier
     */
    public Token getToken() {
        return token;
    }
}