package main.java.com.yourproject.lexer;


import main.java.com.yourproject.tokens.Token;
import main.java.com.yourproject.tokens.TokenType;

import java.util.regex.Pattern;

/**
 * Converts source code into tokens for the Monkey language parser
 */
public class Lexer {
    private final String input;
    private int position;     // current position in input (points to current char)
    private int readPosition; // current reading position in input (after current char)
    private char ch;         // current char under examination

    // Pattern matchers
    private static final Pattern LETTER_PATTERN = Pattern.compile("^[a-zA-Z_]$");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("^\\d$");
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("^\\s$");

    public Lexer(String input) {
        this.input = input;
        readChar();
    }

    /**
     * Returns the next token from the input
     */
    public Token nextToken() {
        Token token;
        skipWhitespace();

        switch (ch) {
            case '=':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.EQ, "==");
                } else {
                    token = new Token(TokenType.ASSIGN, String.valueOf(ch));
                }
                break;
            case '+':
                token = new Token(TokenType.PLUS, String.valueOf(ch));
                break;
            case '-':
                token = new Token(TokenType.MINUS, String.valueOf(ch));
                break;
            case '!':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.NOT_EQ, "!=");
                } else {
                    token = new Token(TokenType.BANG, String.valueOf(ch));
                }
                break;
            case '/':
                token = new Token(TokenType.SLASH, String.valueOf(ch));
                break;
            case '*':
                token = new Token(TokenType.ASTERISK, String.valueOf(ch));
                break;
            case '<':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.LE, "<=");
                } else {
                    token = new Token(TokenType.LT, String.valueOf(ch));
                }
                break;
            case '>':
                if (peekChar() == '=') {
                    readChar();
                    token = new Token(TokenType.GE, ">=");
                } else {
                    token = new Token(TokenType.GT, String.valueOf(ch));
                }
                break;
            case ';':
                token = new Token(TokenType.SEMICOLON, String.valueOf(ch));
                break;
            case ',':
                token = new Token(TokenType.COMMA, String.valueOf(ch));
                break;
            case '(':
                token = new Token(TokenType.LPAREN, String.valueOf(ch));
                break;
            case ')':
                token = new Token(TokenType.RPAREN, String.valueOf(ch));
                break;
            case '{':
                token = new Token(TokenType.LBRACE, String.valueOf(ch));
                break;
            case '}':
                token = new Token(TokenType.RBRACE, String.valueOf(ch));
                break;
            case 0:
                token = new Token(TokenType.EOF, "");
                break;
            default:
                if (isLetter(ch)) {
                    String literal = readIdentifier();
                    return new Token(TokenType.lookupTokenType(literal), literal);
                } else if (isDigit(ch)) {
                    return new Token(TokenType.INT, readNumber());
                } else {
                    token = new Token(TokenType.ILLEGAL, String.valueOf(ch));
                }
        }

        readChar();
        return token;
    }

    /**
     * Reads the next character and advances position
     */
    private void readChar() {
        if (readPosition >= input.length()) {
            ch = 0; // ASCII code for "NUL"
        } else {
            ch = input.charAt(readPosition);
        }
        position = readPosition;
        readPosition++;
    }

    /**
     * Peeks at the next character without consuming it
     */
    private char peekChar() {
        if (readPosition >= input.length()) {
            return 0;
        }
        return input.charAt(readPosition);
    }

    /**
     * Reads an identifier (variable name or keyword)
     */
    private String readIdentifier() {
        int startPos = position;
        while (isLetter(ch)) {
            readChar();
        }
        return input.substring(startPos, position);
    }

    /**
     * Reads a number literal
     */
    private String readNumber() {
        int startPos = position;
        while (isDigit(ch)) {
            readChar();
        }
        return input.substring(startPos, position);
    }

    /**
     * Skips whitespace characters
     */
    private void skipWhitespace() {
        while (WHITESPACE_PATTERN.matcher(String.valueOf(ch)).matches()) {
            readChar();
        }
    }

    /**
     * Checks if character is valid for identifiers
     */
    private boolean isLetter(char c) {
        return LETTER_PATTERN.matcher(String.valueOf(c)).matches();
    }

    /**
     * Checks if character is a digit
     */
    private boolean isDigit(char c) {
        return DIGIT_PATTERN.matcher(String.valueOf(c)).matches();
    }
}
