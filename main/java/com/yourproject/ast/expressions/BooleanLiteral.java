package main.java.com.yourproject.ast.expressions;


import main.java.com.yourproject.tokens.Token;

public class BooleanLiteral implements Expression {
    private final Token token;
    private final boolean value;

    public BooleanLiteral(Token token, boolean value) {
        this.token = token;
        this.value = value;
    }

    public boolean getValue() { return value; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() { return String.valueOf(value); }
}
