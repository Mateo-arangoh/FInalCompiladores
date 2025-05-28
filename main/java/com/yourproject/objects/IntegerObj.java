package main.java.com.yourproject.objects;


/**
 * Represents an integer value in the Monkey language evaluator.
 */
public class IntegerObj implements EvaluatorObject {
    private final long value;

    /**
     * Constructs a new integer object
     * @param value The integer value to wrap
     */
    public IntegerObj(long value) {
        this.value = value;
    }

    /**
     * @return The underlying primitive value
     */
    public long getIntegerValue() {
        return value;
    }

    /**
     * @return The type name "INTEGER"
     */
    @Override
    public String type() {
        return "INTEGER";
    }

    /**
     * @return The string representation of the integer
     */
    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    /**
     * Compares integer objects by value
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        IntegerObj that = (IntegerObj) obj;
        return value == that.value;
    }

    /**
     * @return Hash code based on the integer value
     */
    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    /**
     * @return The string representation for debugging
     */
    @Override
    public String toString() {
        return "IntegerObj{" + value + "}";
    }
}
