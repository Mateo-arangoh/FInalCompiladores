package main.java.com.yourproject.ast.expressions;

import main.java.com.yourproject.tokens.Token;

/**
 * Represents an integer literal expression in the Monkey language.
 * Example: In `let x = 5;`, "5" is an IntegerLiteral.
 */
public class IntegerLiteral implements Expression {
    private final Token token;  // The INT token
    private final int value;    // The parsed integer value

    /**
     * Constructs a new IntegerLiteral node
     * @param token The INT token containing the number
     * @param value The parsed integer value
     */
    public IntegerLiteral(Token token, int value) {
        this.token = token;
        this.value = value;
    }

    /**
     * @return The numeric value of this literal
     */
    public int getValue() {
        return value;
    }

    /**
     * @return The token's literal representation (original string)
     */
    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    /**
     * @return The string representation of the integer value
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }

    // Optional: Useful for testing and debugging
    /**
     * @return The token associated with this integer literal
     */
    public Token getToken() {
        return token;
    }
}
