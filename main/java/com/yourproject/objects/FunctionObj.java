package main.java.com.yourproject.objects;


import main.java.com.yourproject.ast.expressions.Identifier;
import main.java.com.yourproject.ast.statements.BlockStatement;
import main.java.com.yourproject.runtime.Environment;
import java.util.List;

/**
 * Represents a function object in the Monkey language evaluator.
 * Contains the function's parameters, body, and closure environment.
 */
public class FunctionObj implements EvaluatorObject {
    private final List<Identifier> parameters;
    private final BlockStatement body;
    private final Environment env;

    /**
     * Constructs a new function object
     * @param parameters The function parameters
     * @param body The function body
     * @param env The closure environment
     */
    public FunctionObj(List<Identifier> parameters,
                       BlockStatement body,
                       Environment env) {
        this.parameters = parameters;
        this.body = body;
        this.env = env;
    }

    /**
     * @return The function's parameters
     */
    public List<Identifier> getParameters() {
        return parameters;
    }

    /**
     * @return The function's body
     */
    public BlockStatement getBody() {
        return body;
    }

    /**
     * @return The closure environment
     */
    public Environment getEnv() {
        return env;
    }

    /**
     * @return The type name "FUNCTION"
     */
    @Override
    public String type() {
        return "FUNCTION";
    }

    /**
     * @return A string representation of the function signature
     */
    @Override
    public String inspect() {
        StringBuilder sb = new StringBuilder();
        sb.append("fn(");

        for (int i = 0; i < parameters.size(); i++) {
            sb.append(parameters.get(i));
            if (i < parameters.size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(") {\n");
        sb.append(body);
        sb.append("\n}");

        return sb.toString();
    }

    /**
     * @return Detailed string representation for debugging
     */
    @Override
    public String toString() {
        return "FunctionObj{" +
                "parameters=" + parameters +
                ", body=" + body +
                ", env=" + env +
                '}';
    }
}
