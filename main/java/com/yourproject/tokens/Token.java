package main.java.com.yourproject.tokens;


/**
 * Represents a lexical token in the Monkey programming language
 */
public class Token {
    private final TokenType type;
    private final String literal;

    /**
     * Constructs a new token
     * @param type The token type from TokenType enum
     * @param literal The actual literal value from source code
     */
    public Token(TokenType type, String literal) {
        this.type = type;
        this.literal = literal;
    }

    /**
     * @return The token's type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * @return The literal string value
     */
    public String getLiteral() {
        return literal;
    }

    /**
     * @return A formatted string representation for debugging
     */
    @Override
    public String toString() {
        return String.format("Token[type=%s, literal=%s]", type, literal);
    }

    /**
     * Compares tokens by both type and literal value
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Token other = (Token) obj;
        return type == other.type && literal.equals(other.literal);
    }

    /**
     * @return A hash code combining type and literal
     */
    @Override
    public int hashCode() {
        return 31 * type.hashCode() + literal.hashCode();
    }
}
