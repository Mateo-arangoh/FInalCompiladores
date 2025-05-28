package main.java.com.yourproject.tokens;

import java.util.Map;
import java.util.HashMap;

public enum TokenType {
    // Special types
    ILLEGAL("ILLEGAL"),
    EOF("EOF"),

    // Identifiers + literals
    IDENT("IDENT"),
    INT("INT"),
    STRING("STRING"),

    // Operators
    ASSIGN("="),
    PLUS("+"),
    MINUS("-"),
    BANG("!"),
    ASTERISK("*"),
    SLASH("/"),
    LT("<"),
    GT(">"),
    EQ("=="),
    NOT_EQ("!="),
    LE("<="),
    GE(">="),

    // Delimiters
    COMMA(","),
    SEMICOLON(";"),
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),

    // Keywords
    FUNCTION("fn"),
    LET("let"),
    TRUE("true"),
    FALSE("false"),
    IF("if"),
    ELSE("else"),
    RETURN("return"),
    WHILE("while"),
    FOR("for");

    private final String literal;
    private static final Map<String, TokenType> keywordMap = new HashMap<>();

    static {
        keywordMap.put("fn", FUNCTION);
        keywordMap.put("let", LET);
        keywordMap.put("true", TRUE);
        keywordMap.put("false", FALSE);
        keywordMap.put("if", IF);
        keywordMap.put("else", ELSE);
        keywordMap.put("return", RETURN);
        keywordMap.put("while", WHILE);
        keywordMap.put("for", FOR);
    }

    TokenType(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public static TokenType lookupTokenType(String ident) {
        return keywordMap.getOrDefault(ident, IDENT);
    }
}