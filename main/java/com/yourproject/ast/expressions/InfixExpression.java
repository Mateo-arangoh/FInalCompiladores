package main.java.com.yourproject.ast.expressions;

import main.java.com.yourproject.tokens.Token;

public class InfixExpression implements Expression {
    private final Token token;
    private final Expression left;
    private final String operator;
    private final Expression right;

    public InfixExpression(Token token, Expression left, String operator, Expression right) {
        this.token = token;
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() { return left; }
    public String getOperator() { return operator; }
    public Expression getRight() { return right; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() { return "(" + left + " " + operator + " " + right + ")"; }
}