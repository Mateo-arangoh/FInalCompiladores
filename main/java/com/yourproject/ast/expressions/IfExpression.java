package main.java.com.yourproject.ast.expressions;



import main.java.com.yourproject.ast.statements.BlockStatement;
import main.java.com.yourproject.tokens.Token;

public class IfExpression implements Expression {
    private final Token token;
    private final Expression condition;
    private final BlockStatement consequence;
    private final BlockStatement alternative;

    public IfExpression(Token token, Expression condition,
                        BlockStatement consequence, BlockStatement alternative) {
        this.token = token;
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    public Expression getCondition() { return condition; }
    public BlockStatement getConsequence() { return consequence; }
    public BlockStatement getAlternative() { return alternative; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() {
        String out = "if " + condition + " " + consequence;
        if (alternative != null) out += " else " + alternative;
        return out;
    }
}
