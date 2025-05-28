package main.java.com.yourproject.parser;


import main.java.com.yourproject.ast.*;
import main.java.com.yourproject.ast.expressions.*;
import main.java.com.yourproject.ast.statements.*;
import main.java.com.yourproject.lexer.Lexer;
import main.java.com.yourproject.tokens.Token;
import main.java.com.yourproject.tokens.TokenType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Parses tokens into an Abstract Syntax Tree (AST) for the Monkey language.
 */
public class Parser {
    private final Lexer lexer;
    private Token currentToken;
    private Token peekToken;
    private final List<String> errors = new ArrayList<>();

    // Precedence levels
    private enum Precedence {
        LOWEST,
        EQUALS,      // ==
        LESSGREATER, // > or <
        SUM,         // +
        PRODUCT,     // *
        PREFIX,      // -X or !X
        CALL,        // myFunction(X)
        INDEX        // array[index]
    }

    // Precedence table
    private final java.util.Map<TokenType, Precedence> precedences = Map.of(
            TokenType.EQ, Precedence.EQUALS,
            TokenType.NOT_EQ, Precedence.EQUALS,
            TokenType.LT, Precedence.LESSGREATER,
            TokenType.GT, Precedence.LESSGREATER,
            TokenType.PLUS, Precedence.SUM,
            TokenType.MINUS, Precedence.SUM,
            TokenType.SLASH, Precedence.PRODUCT,
            TokenType.ASTERISK, Precedence.PRODUCT,
            TokenType.LPAREN, Precedence.CALL
    );

    // Parse function interfaces
    private interface PrefixParseFn {
        Expression parse();
    }

    private interface InfixParseFn {
        Expression parse(Expression expression);
    }

    // Parse function maps
    private final java.util.Map<TokenType, PrefixParseFn> prefixParseFns = new java.util.HashMap<>();
    private final java.util.Map<TokenType, InfixParseFn> infixParseFns = new java.util.HashMap<>();

    public Parser(Lexer lexer) {
        this.lexer = lexer;

        // Initialize prefix parse functions
        prefixParseFns.put(TokenType.IDENT, this::parseIdentifier);
        prefixParseFns.put(TokenType.INT, this::parseIntegerLiteral);
        prefixParseFns.put(TokenType.BANG, this::parsePrefixExpression);
        prefixParseFns.put(TokenType.MINUS, this::parsePrefixExpression);
        prefixParseFns.put(TokenType.TRUE, this::parseBoolean);
        prefixParseFns.put(TokenType.FALSE, this::parseBoolean);
        prefixParseFns.put(TokenType.LPAREN, this::parseGroupedExpression);
        prefixParseFns.put(TokenType.IF, this::parseIfExpression);
        prefixParseFns.put(TokenType.FUNCTION, this::parseFunctionLiteral);

        // Initialize infix parse functions
        infixParseFns.put(TokenType.PLUS, this::parseInfixExpression);
        infixParseFns.put(TokenType.MINUS, this::parseInfixExpression);
        infixParseFns.put(TokenType.SLASH, this::parseInfixExpression);
        infixParseFns.put(TokenType.ASTERISK, this::parseInfixExpression);
        infixParseFns.put(TokenType.EQ, this::parseInfixExpression);
        infixParseFns.put(TokenType.NOT_EQ, this::parseInfixExpression);
        infixParseFns.put(TokenType.LT, this::parseInfixExpression);
        infixParseFns.put(TokenType.GT, this::parseInfixExpression);
        infixParseFns.put(TokenType.LPAREN, this::parseCallExpression);

        // Read two tokens to initialize current and peek
        nextToken();
        nextToken();
    }

    public Program parseProgram() {
        Program program = new Program();

        while (currentToken.getType() != TokenType.EOF) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                program.getStatements().add(stmt);
            }
            nextToken();
        }

        return program;
    }

    private Statement parseStatement() {
        return switch (currentToken.getType()) {
            case LET -> parseLetStatement();
            case RETURN -> parseReturnStatement();
            default -> parseExpressionStatement();
        };
    }

    private LetStatement parseLetStatement() {
        Token token = currentToken;

        if (!expectPeek(TokenType.IDENT)) {
            return null;
        }

        Identifier name = new Identifier(currentToken, currentToken.getLiteral());

        if (!expectPeek(TokenType.ASSIGN)) {
            return null;
        }

        nextToken();
        Expression value = parseExpression(Precedence.LOWEST.ordinal());

        if (peekToken.getType() == TokenType.SEMICOLON) {
            nextToken();
        }

        return new LetStatement(token, name, value);
    }

    private ReturnStatement parseReturnStatement() {
        Token token = currentToken;
        nextToken();

        Expression returnValue = parseExpression(Precedence.LOWEST.ordinal());

        if (peekToken.getType() == TokenType.SEMICOLON) {
            nextToken();
        }

        return new ReturnStatement(token, returnValue);
    }

    private ExpressionStatement parseExpressionStatement() {
        Token token = currentToken;
        Expression expression = parseExpression(Precedence.LOWEST.ordinal());

        if (peekToken.getType() == TokenType.SEMICOLON) {
            nextToken();
        }

        return new ExpressionStatement(token, expression);
    }

    private Expression parseExpression(int precedence) {
        PrefixParseFn prefix = prefixParseFns.get(currentToken.getType());
        if (prefix == null) {
            noPrefixParseFnError(currentToken.getType());
            return null;
        }

        Expression leftExp = prefix.parse();

        while (peekToken.getType() != TokenType.SEMICOLON && precedence < peekPrecedence()) {
            InfixParseFn infix = infixParseFns.get(peekToken.getType());
            if (infix == null) {
                return leftExp;
            }

            nextToken();
            leftExp = infix.parse(leftExp);
        }

        return leftExp;
    }

    private Expression parseIdentifier() {
        return new Identifier(currentToken, currentToken.getLiteral());
    }

    private Expression parseIntegerLiteral() {
        try {
            return new IntegerLiteral(currentToken, Integer.parseInt(currentToken.getLiteral()));
        } catch (NumberFormatException e) {
            errors.add("could not parse " + currentToken.getLiteral() + " as integer");
            return null;
        }
    }

    private Expression parsePrefixExpression() {
        Token token = currentToken;
        String operator = currentToken.getLiteral();

        nextToken();
        Expression right = parseExpression(Precedence.PREFIX.ordinal());

        return new PrefixExpression(token, operator, right);
    }

    private Expression parseInfixExpression(Expression left) {
        Token token = currentToken;
        String operator = currentToken.getLiteral();

        int precedence = currentPrecedence();
        nextToken();
        Expression right = parseExpression(precedence);

        return new InfixExpression(token, left, operator, right);
    }

    private Expression parseBoolean() {
        return new BooleanLiteral(currentToken, currentToken.getType() == TokenType.TRUE);
    }

    private Expression parseGroupedExpression() {
        nextToken();
        Expression exp = parseExpression(Precedence.LOWEST.ordinal());

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return exp;
    }

    private Expression parseIfExpression() {
        Token token = currentToken;

        if (!expectPeek(TokenType.LPAREN)) {
            return null;
        }

        nextToken();
        Expression condition = parseExpression(Precedence.LOWEST.ordinal());

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        if (!expectPeek(TokenType.LBRACE)) {
            return null;
        }

        BlockStatement consequence = parseBlockStatement();

        BlockStatement alternative = null;
        if (peekToken.getType() == TokenType.ELSE) {
            nextToken();

            if (!expectPeek(TokenType.LBRACE)) {
                return null;
            }

            alternative = parseBlockStatement();
        }

        return new IfExpression(token, condition, consequence, alternative);
    }

    private BlockStatement parseBlockStatement() {
        Token token = currentToken;
        BlockStatement block = new BlockStatement(token);

        nextToken();

        while (currentToken.getType() != TokenType.RBRACE &&
                currentToken.getType() != TokenType.EOF) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                block.getStatements().add(stmt);
            }
            nextToken();
        }

        return block;
    }

    private Expression parseFunctionLiteral() {
        Token token = currentToken;

        if (!expectPeek(TokenType.LPAREN)) {
            return null;
        }

        List<Identifier> parameters = parseFunctionParameters();

        if (!expectPeek(TokenType.LBRACE)) {
            return null;
        }

        BlockStatement body = parseBlockStatement();

        return new FunctionLiteral(token, parameters, body);
    }

    private List<Identifier> parseFunctionParameters() {
        List<Identifier> identifiers = new ArrayList<>();

        if (peekToken.getType() == TokenType.RPAREN) {
            nextToken();
            return identifiers;
        }

        nextToken();
        identifiers.add(new Identifier(currentToken, currentToken.getLiteral()));

        while (peekToken.getType() == TokenType.COMMA) {
            nextToken();
            nextToken();
            identifiers.add(new Identifier(currentToken, currentToken.getLiteral()));
        }

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return identifiers;
    }

    private Expression parseCallExpression(Expression function) {
        Token token = currentToken;
        List<Expression> arguments = parseCallArguments();
        return new CallExpression(token, function, arguments);
    }

    private List<Expression> parseCallArguments() {
        List<Expression> args = new ArrayList<>();

        if (peekToken.getType() == TokenType.RPAREN) {
            nextToken();
            return args;
        }

        nextToken();
        args.add(parseExpression(Precedence.LOWEST.ordinal()));

        while (peekToken.getType() == TokenType.COMMA) {
            nextToken();
            nextToken();
            args.add(parseExpression(Precedence.LOWEST.ordinal()));
        }

        if (!expectPeek(TokenType.RPAREN)) {
            return null;
        }

        return args;
    }

    private void nextToken() {
        currentToken = peekToken;
        peekToken = lexer.nextToken();
    }

    private boolean expectPeek(TokenType type) {
        if (peekToken.getType() == type) {
            nextToken();
            return true;
        }
        peekError(type);
        return false;
    }

    private int peekPrecedence() {
        return precedences.getOrDefault(peekToken.getType(), Precedence.LOWEST).ordinal();
    }

    private int currentPrecedence() {
        return precedences.getOrDefault(currentToken.getType(), Precedence.LOWEST).ordinal();
    }

    private void peekError(TokenType type) {
        String msg = String.format("expected next token to be %s, got %s instead",
                type, peekToken.getType());
        errors.add(msg);
    }

    private void noPrefixParseFnError(TokenType type) {
        errors.add("no prefix parse function for " + type + " found");
    }

    public List<String> getErrors() {
        return errors;
    }
}
