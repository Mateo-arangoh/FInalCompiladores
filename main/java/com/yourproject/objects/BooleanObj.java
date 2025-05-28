package main.java.com.yourproject.objects;


/**
 * Represents a boolean value in the Monkey language evaluator.
 * Uses the singleton pattern for TRUE and FALSE values.
 */
public class BooleanObj implements EvaluatorObject {
    // Singleton instances
    public static final BooleanObj TRUE = new BooleanObj(true);
    public static final BooleanObj FALSE = new BooleanObj(false);

    private final boolean value;

    /**
     * Private constructor to enforce singleton pattern
     * @param value The boolean value to wrap
     */
    private BooleanObj(boolean value) {
        this.value = value;
    }

    /**
     * @return The underlying boolean value
     */
    public boolean getBooleanValue() {
        return value;
    }

    /**
     * @return The type name "BOOLEAN"
     */
    @Override
    public String type() {
        return "BOOLEAN";
    }

    /**
     * @return "true" or "false" string representation
     */
    @Override
    public String inspect() {
        return String.valueOf(value);
    }

    /**
     * Compares boolean objects by value
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BooleanObj that = (BooleanObj) obj;
        return value == that.value;
    }

    /**
     * @return Hash code based on boolean value
     */
    @Override
    public int hashCode() {
        return Boolean.hashCode(value);
    }

    /**
     * @return The string representation for debugging
     */
    @Override
    public String toString() {
        return "BooleanObj{" + value + "}";
    }
}
