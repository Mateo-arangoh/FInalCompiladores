package main.java.com.yourproject.runtime;


import main.java.com.yourproject.objects.EvaluatorObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages variable scoping and storage during evaluation
 */
public class Environment {
    private final Map<String, EvaluatorObject> store;
    private final Environment outer;

    /**
     * Creates a new global environment
     */
    public Environment() {
        this(null);
    }

    /**
     * Creates a new enclosed environment
     * @param outer The parent environment
     */
    public Environment(Environment outer) {
        this.store = new HashMap<>();
        this.outer = outer;
    }

    /**
     * Retrieves a variable from the environment
     * @param name The variable name
     * @return The stored object or null if not found
     */
    public EvaluatorObject get(String name) {
        EvaluatorObject obj = store.get(name);
        if (obj == null && outer != null) {
            return outer.get(name); // Look in parent scope
        }
        return obj;
    }

    /**
     * Sets a variable in the current environment
     * @param name The variable name
     * @param value The value to store
     * @return The stored value
     */
    public EvaluatorObject set(String name, EvaluatorObject value) {
        store.put(name, value);
        return value;
    }

    /**
     * Checks if a variable exists in any scope
     * @param name The variable name
     * @return true if the variable exists
     */
    public boolean exists(String name) {
        if (store.containsKey(name)) return true;
        return outer != null && outer.exists(name);
    }

    /**
     * Creates a new enclosed environment with this as its outer
     * @return The new child environment
     */
    public Environment newEnclosedEnvironment() {
        return new Environment(this);
    }
}
