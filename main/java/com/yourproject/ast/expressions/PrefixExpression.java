package main.java.com.yourproject.ast.expressions;

import main.java.com.yourproject.tokens.Token;

public class PrefixExpression implements Expression {
    private final Token token;
    private final String operator;
    private final Expression right;

    public PrefixExpression(Token token, String operator, Expression right) {
        this.token = token;
        this.operator = operator;
        this.right = right;
    }

    public String getOperator() { return operator; }
    public Expression getRight() { return right; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() { return "(" + operator + right + ")"; }
}