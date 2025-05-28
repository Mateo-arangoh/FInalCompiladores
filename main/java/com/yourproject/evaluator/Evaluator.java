package main.java.com.yourproject.evaluator;

import main.java.com.yourproject.ast.Program;
import main.java.com.yourproject.ast.*;
import main.java.com.yourproject.ast.expressions.*;
import main.java.com.yourproject.ast.statements.*;
import main.java.com.yourproject.objects.*;
import main.java.com.yourproject.runtime.Environment;
import java.util.List;

/**
 * Evaluates AST nodes and produces runtime values
 */
public class Evaluator {
    public static final BooleanObj TRUE = BooleanObj.TRUE;
    public static final BooleanObj FALSE = BooleanObj.FALSE;
    public static final NullObj NULL = NullObj.NULL;

    /**
     * Main evaluation method
     * @param node The AST node to evaluate
     * @param env The current environment
     * @return The evaluated object
     */
    public EvaluatorObject eval(Node node, Environment env) {
        // Program evaluation
        if (node instanceof Program) {
            return evalProgram((Program) node, env);
        }

        // Statement evaluations
        if (node instanceof ExpressionStatement) {
            return eval(((ExpressionStatement) node).getExpression(), env);
        }

        if (node instanceof LetStatement) {
            LetStatement letStmt = (LetStatement) node;
            EvaluatorObject value = eval(letStmt.getValue(), env);
            if (isError(value)) return value;
            env.set(letStmt.getName().getValue(), value);
            return value;
        }

        // Expression evaluations
        if (node instanceof IntegerLiteral) {
            return new IntegerObj(((IntegerLiteral) node).getValue());
        }

        if (node instanceof BooleanLiteral) {
            return ((BooleanLiteral) node).getValue() ? TRUE : FALSE;
        }

        if (node instanceof Identifier) {
            return evalIdentifier((Identifier) node, env);
        }

        if (node instanceof PrefixExpression) {
            EvaluatorObject right = eval(((PrefixExpression) node).getRight(), env);
            if (isError(right)) return right;
            return evalPrefixExpression(((PrefixExpression) node).getOperator(), right);
        }

        if (node instanceof InfixExpression) {
            EvaluatorObject left = eval(((InfixExpression) node).getLeft(), env);
            if (isError(left)) return left;

            EvaluatorObject right = eval(((InfixExpression) node).getRight(), env);
            if (isError(right)) return right;

            return evalInfixExpression(((InfixExpression) node).getOperator(), left, right);
        }

        if (node instanceof BlockStatement) {
            return evalBlockStatement((BlockStatement) node, env);
        }

        if (node instanceof IfExpression) {
            return evalIfExpression((IfExpression) node, env);
        }

        if (node instanceof ReturnStatement) {
            EvaluatorObject value = eval(((ReturnStatement) node).getReturnValue(), env);
            return new ReturnObj(value);
        }

        return NULL;
    }

    private EvaluatorObject evalProgram(Program program, Environment env) {
        EvaluatorObject result = NULL;
        for (Statement stmt : program.getStatements()) {
            result = eval(stmt, env);

            if (result instanceof ReturnObj) {
                return ((ReturnObj) result).getValue();
            }

            if (result instanceof ErrorObj) {
                return result;
            }
        }
        return result;
    }

    private EvaluatorObject evalBlockStatement(BlockStatement block, Environment env) {
        EvaluatorObject result = NULL;
        for (Statement stmt : block.getStatements()) {
            result = eval(stmt, env);

            if (result != null && (result instanceof ReturnObj || result instanceof ErrorObj)) {
                return result;
            }
        }
        return result;
    }

    private EvaluatorObject evalPrefixExpression(String operator, EvaluatorObject right) {
        switch (operator) {
            case "!":
                return evalBangOperator(right);
            case "-":
                return evalMinusPrefixOperator(right);
            default:
                return new ErrorObj("unknown operator: " + operator + right.type());
        }
    }

    private EvaluatorObject evalInfixExpression(String operator, EvaluatorObject left, EvaluatorObject right) {
        if (left instanceof IntegerObj && right instanceof IntegerObj) {
            return evalIntegerInfixExpression(operator, (IntegerObj) left, (IntegerObj) right);
        }

        if (operator.equals("==")) {
            return nativeBoolToBooleanObj(left == right);
        }

        if (operator.equals("!=")) {
            return nativeBoolToBooleanObj(left != right);
        }

        if (left.type() != right.type()) {
            return new ErrorObj("type mismatch: " + left.type() + " " + operator + " " + right.type());
        }

        return new ErrorObj("unknown operator: " + left.type() + " " + operator + " " + right.type());
    }

    private EvaluatorObject evalIntegerInfixExpression(String operator, IntegerObj left, IntegerObj right) {
        long leftVal = left.getIntegerValue();
        long rightVal = right.getIntegerValue();
        switch (operator) {
            case "+":
                return new IntegerObj(leftVal + rightVal);
            case "-":
                return new IntegerObj(leftVal - rightVal);
            case "*":
                return new IntegerObj(leftVal * rightVal);
            case "/":
                return new IntegerObj(leftVal / rightVal);
            case "<":
                return nativeBoolToBooleanObj(leftVal < rightVal);
            case ">":
                return nativeBoolToBooleanObj(leftVal > rightVal);
            case "==":
                return nativeBoolToBooleanObj(leftVal == rightVal);
            case "!=":
                return nativeBoolToBooleanObj(leftVal != rightVal);
            default:
                return new ErrorObj("unknown operator: " + left.type() + " " + operator + " " + right.type());
        }
    }

    private EvaluatorObject evalIfExpression(IfExpression ie, Environment env) {
        EvaluatorObject condition = eval(ie.getCondition(), env);
        if (isError(condition)) return condition;

        if (isTruthy(condition)) {
            return eval(ie.getConsequence(), env);
        } else if (ie.getAlternative() != null) {
            return eval(ie.getAlternative(), env);
        } else {
            return NULL;
        }
    }

    private EvaluatorObject evalIdentifier(Identifier node, Environment env) {
        EvaluatorObject value = env.get(node.getValue());
        if (value == null) {
            return new ErrorObj("identifier not found: " + node.getValue());
        }
        return value;
    }

    private boolean isTruthy(EvaluatorObject obj) {
        if (obj == NULL) return false;
        if (obj == TRUE) return true;
        if (obj == FALSE) return false;
        return true;
    }

    private BooleanObj nativeBoolToBooleanObj(boolean input) {
        return input ? TRUE : FALSE;
    }

    private EvaluatorObject evalBangOperator(EvaluatorObject right) {
        if (right == TRUE) return FALSE;
        if (right == FALSE) return TRUE;
        if (right == NULL) return TRUE;
        return FALSE;
    }

    private EvaluatorObject evalMinusPrefixOperator(EvaluatorObject right) {
        if (!(right instanceof IntegerObj)) {
            return new ErrorObj("unknown operator: -" + right.type());
        }
        return new IntegerObj(-((IntegerObj) right).getIntegerValue());
    }

    private boolean isError(EvaluatorObject obj) {
        return obj != null && obj instanceof ErrorObj;
    }
}
