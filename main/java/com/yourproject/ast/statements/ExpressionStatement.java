package main.java.com.yourproject.ast.statements;


import main.java.com.yourproject.ast.statements.Statement;
import main.java.com.yourproject.ast.expressions.Expression;
import main.java.com.yourproject.tokens.Token;

public class ExpressionStatement implements Statement {
    private final Token token;
    private final Expression expression;

    public ExpressionStatement(Token token, Expression expression) {
        this.token = token;
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() { return expression.toString(); }
}
