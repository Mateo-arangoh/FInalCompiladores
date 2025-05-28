package main.java.com.yourproject.ast.expressions;


import main.java.com.yourproject.ast.expressions.Identifier;
import main.java.com.yourproject.ast.statements.BlockStatement;
import main.java.com.yourproject.tokens.Token;
import java.util.List;

public class FunctionLiteral implements Expression {
    private final Token token; // The 'fn' token
    private final List<Identifier> parameters;
    private final BlockStatement body;

    public FunctionLiteral(Token token, List<Identifier> parameters,
                           BlockStatement body) {
        this.token = token;
        this.parameters = parameters;
        this.body = body;
    }

    public List<Identifier> getParameters() { return parameters; }
    public BlockStatement getBody() { return body; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() {
        return "fn(" + String.join(", ",
                parameters.stream().map(Object::toString).toArray(String[]::new)) + ") " + body;
    }
}