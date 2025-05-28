package main.java.com.yourproject.objects;


/**
 * The base interface for all evaluator objects in the Monkey language.
 * Represents values that can be produced during evaluation of expressions.
 */
public interface EvaluatorObject {
    /**
     * Returns the type of the object as a string
     * @return The object type (e.g., "INTEGER", "BOOLEAN", "FUNCTION")
     */
    String type();

    /**
     * Returns a string representation of the object's value
     * @return The inspectable value as a string
     */
    String inspect();

    /**
     * Optional: Returns the underlying Java object value
     * @return The raw value (implementation specific)
     */
    default Object getValue() {
        return this;
    }
}