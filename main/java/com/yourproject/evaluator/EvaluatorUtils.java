package main.java.com.yourproject.evaluator;


import main.java.com.yourproject.objects.*;

/**
 * Utility class containing helper methods for the Evaluator
 */
public class EvaluatorUtils {

    /**
     * Checks if an object is considered truthy in Monkey language
     * @param obj The object to check
     * @return true if truthy, false otherwise
     */
    public static boolean isTruthy(EvaluatorObject obj) {
        if (obj == BooleanObj.FALSE) return false;
        if (obj == BooleanObj.TRUE) return true;
        if (obj == NullObj.NULL) return false;
        return true;
    }

    /**
     * Converts a Java boolean to a Monkey language BooleanObj
     * @param value The boolean value to convert
     * @return BooleanObj.TRUE or BooleanObj.FALSE
     */
    public static BooleanObj nativeBoolToBooleanObj(boolean value) {
        return value ? BooleanObj.TRUE : BooleanObj.FALSE;
    }

    /**
     * Checks if an object is an error
     * @param obj The object to check
     * @return true if the object is an ErrorObj, false otherwise
     */
    public static boolean isError(EvaluatorObject obj) {
        return obj != null && obj instanceof ErrorObj;
    }

    /**
     * Evaluates a prefix operator expression
     * @param operator The prefix operator (! or -)
     * @param right The right operand
     * @return The evaluation result
     */
    public static EvaluatorObject evalPrefixExpression(String operator, EvaluatorObject right) {
        switch (operator) {
            case "!":
                return evalBangOperator(right);
            case "-":
                return evalMinusPrefixOperator(right);
            default:
                return new ErrorObj("unknown operator: " + operator + right.type());
        }
    }

    /**
     * Evaluates the ! operator
     * @param right The operand
     * @return The negated boolean value
     */
    public static EvaluatorObject evalBangOperator(EvaluatorObject right) {
        if (right == BooleanObj.TRUE) return BooleanObj.FALSE;
        if (right == BooleanObj.FALSE) return BooleanObj.TRUE;
        if (right == NullObj.NULL) return BooleanObj.TRUE;
        return BooleanObj.FALSE;
    }

    /**
     * Evaluates the - operator
     * @param right The operand
     * @return The negated integer value
     */
    public static EvaluatorObject evalMinusPrefixOperator(EvaluatorObject right) {
        if (!(right instanceof IntegerObj)) {
            return new ErrorObj("unknown operator: -" + right.type());
        }
        return new IntegerObj(-((IntegerObj) right).getIntegerValue());
    }

    /**
     * Evaluates an infix expression between two integers
     * @param operator The infix operator
     * @param left The left operand
     * @param right The right operand
     * @return The evaluation result
     */
    public static EvaluatorObject evalIntegerInfixExpression(
            String operator,
            IntegerObj left,
            IntegerObj right) {
        long leftVal = left.getIntegerValue();
        long rightVal = right.getIntegerValue();

        switch (operator) {
            case "+": return new IntegerObj(leftVal + rightVal);
            case "-": return new IntegerObj(leftVal - rightVal);
            case "*": return new IntegerObj(leftVal * rightVal);
            case "/": return new IntegerObj(leftVal / rightVal);
            case "<": return nativeBoolToBooleanObj(leftVal < rightVal);
            case ">": return nativeBoolToBooleanObj(leftVal > rightVal);
            case "==": return nativeBoolToBooleanObj(leftVal == rightVal);
            case "!=": return nativeBoolToBooleanObj(leftVal != rightVal);
            default:
                return new ErrorObj(
                        "unknown operator: " + left.type() + " " + operator + " " + right.type()
                );
        }
    }
}
