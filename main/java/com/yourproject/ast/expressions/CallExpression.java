package main.java.com.yourproject.ast.expressions;


import main.java.com.yourproject.tokens.Token;
import java.util.List;

public class CallExpression implements Expression {
    private final Token token; // The '(' token
    private final Expression function; // Identifier or FunctionLiteral
    private final List<Expression> arguments;

    public CallExpression(Token token, Expression function,
                          List<Expression> arguments) {
        this.token = token;
        this.function = function;
        this.arguments = arguments;
    }

    public Expression getFunction() { return function; }
    public List<Expression> getArguments() { return arguments; }

    @Override public String tokenLiteral() { return token.getLiteral(); }
    @Override public String toString() {
        return function + "(" + String.join(", ",
                arguments.stream().map(Object::toString).toArray(String[]::new)) + ")";
    }
}