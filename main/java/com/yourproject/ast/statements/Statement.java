package main.java.com.yourproject.ast.statements;


import main.java.com.yourproject.ast.Node;

/**
 * Marker interface for all statement nodes in the AST.
 * Statements perform actions but don't produce values.
 */
public interface Statement extends Node {
    // All statement-specific methods will be defined in concrete implementations
    // This serves as a type marker for the parser and evaluator
}
