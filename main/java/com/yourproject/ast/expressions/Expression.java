package main.java.com.yourproject.ast.expressions;


import main.java.com.yourproject.ast.Node;

/**
 * Marker interface for all expression nodes in the AST.
 * Expressions produce values when evaluated.
 */
public interface Expression extends Node {
    // All expression-specific methods will be defined in concrete implementations
    // This serves as a type marker for the parser and evaluator
}
