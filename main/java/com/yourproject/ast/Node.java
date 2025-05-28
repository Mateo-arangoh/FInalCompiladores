package main.java.com.yourproject.ast;

/**
 * The root interface for all AST nodes in the Monkey language.
 * Every node in the abstract syntax tree must implement this interface.
 */
public interface Node {
    /**
     * Returns the literal value of the token associated with this node
     * @return The token's literal value as a String
     */
    String tokenLiteral();

    /**
     * Returns a string representation of the node for debugging and printing
     * @return The string representation of the node
     */
    String toString();
}
