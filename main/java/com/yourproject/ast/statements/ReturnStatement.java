package main.java.com.yourproject.ast.statements;


import main.java.com.yourproject.ast.expressions.Expression;
import main.java.com.yourproject.tokens.Token;

public class ReturnStatement implements Statement {
    private final Token token;
    private final Expression returnValue;

    public ReturnStatement(Token token, Expression returnValue) {
        this.token = token;
        this.returnValue = returnValue;
    }

    public Expression getReturnValue() { return returnValue; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() { return "return " + returnValue + ";"; }
}
