package main.java.com.yourproject.ast.statements;


import main.java.com.yourproject.ast.expressions.Identifier;
import main.java.com.yourproject.ast.expressions.Expression;
import main.java.com.yourproject.tokens.Token;

/**
 * Represents a variable declaration in the Monkey language.
 * Example: 'let x = 5;' or 'let name = "Alice";'
 */
public class LetStatement implements Statement {
    private final Token token; // The 'let' token
    private final Identifier name; // The variable name
    private final Expression value; // The assigned expression

    /**
     * Constructs a new LetStatement
     * @param token The 'let' token
     * @param name The variable identifier
     * @param value The expression being assigned
     */
    public LetStatement(Token token, Identifier name, Expression value) {
        this.token = token;
        this.name = name;
        this.value = value;
    }

    /**
     * @return The variable name identifier
     */
    public Identifier getName() {
        return name;
    }

    /**
     * @return The assigned value expression
     */
    public Expression getValue() {
        return value;
    }

    /**
     * @return The literal 'let' token string
     */
    @Override
    public String tokenLiteral() {
        return token.getLiteral();
    }

    /**
     * @return The string representation of the entire statement
     */
    @Override
    public String toString() {
        return tokenLiteral() + " " + name + " = " + value + ";";
    }

    /**
     * @return The token associated with this statement
     */
    public Token getToken() {
        return token;
    }
}
