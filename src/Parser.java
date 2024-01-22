import java.io.BufferedReader;
import java.io.IOException;

public class Parser {
    private LexicalAnalyzer lexer;
    private Token currentToken;

    public Parser(BufferedReader inFile) {
        this.lexer = new LexicalAnalyzer(inFile);
        this.currentToken = lexer.scan();
    }

    public void parseModuleDecl() {
        parseModuleHeading();
        parseDeclarations();
        parseProcedureDecl();
        parseBlock();
        parseName();
        match(TokenType.EOF);
    }

    private void parseModuleHeading(){
        // Expecting a 'MODULE' keyword
        match(TokenType.MODULE);

        // Parsing the module name
        parseName();

        // Expecting a semicolon after the module name
        match(TokenType.SEMICOLON);
    }
    private void parseDeclarations() {
        parseConstDecl();
        parseVarDecl();
    }
    private void parseProcedureDecl() {
        while (currentToken.getType() == TokenType.PROCEDURE) {
            parseProcedureHeading();
            parseDeclarations();
            parseBlock();
            parseName();
            match(TokenType.SEMICOLON);
        }
    }
    private void parseBlock() {
        match(TokenType.BEGIN);
        parseStmtList();
        match(TokenType.END);
    }
    private void parseName() {
        if (currentToken.getType() == TokenType.NAME) {
            match(TokenType.NAME);
        } else {
            error("Expected a NAME token.");
        }
    }

    private void parseConstDecl() {
        if (currentToken.getType() == TokenType.CONST) {
            match(TokenType.CONST);
            parseConstList();
        }
    }
    private void parseConstList() {
        while (currentToken.getType() == TokenType.NAME) {
            parseName();
            match(TokenType.ASSIGNMENT_OPERATOR);
            match(TokenType.CONSTANT);
            match(TokenType.SEMICOLON);
        }
    }


    private void parseVarDecl() {
        if (currentToken.getType() == TokenType.VAR) {
            match(TokenType.VAR);
            parseVarList();
        }
    }
    private void parseVarList() {

        while (currentToken.getType() == TokenType.NAME) {
            parseName();
            match(TokenType.COLON);
            parseDataType();
            match(TokenType.SEMICOLON);
        }
    }

    private void parseDataType() {
        TokenType dataType = currentToken.getType();
        if (dataType == TokenType.INTEGER || dataType == TokenType.REAL || dataType == TokenType.CHAR) {
            match(dataType);
        } else {
            error("Expected INTEGER, REAL, or CHAR in data type declaration.");
        }
    }
    private void parseProcedureHeading() {
        match(TokenType.PROCEDURE);
        parseName();
        match(TokenType.SEMICOLON);
    }


    private void parseStmtList() {
        parseStatement();
        while (currentToken.getType() == TokenType.SEMICOLON) {
            match(TokenType.SEMICOLON);
//            System.out.println(currentToken.getLexeme());
            parseStatement();
        }
    }

    private void parseStatement() {
        switch (currentToken.getType()) {
            case NAME:
                parseAssStmt();      // Assignment statement
                break;
            case READINT:
                parseReadIntStmt();     // Read integer statement
                break;
            case READREAL:
                parseReadRealStmt(); // Read real statement
                break;
            case READCHAR:
                parseReadCharStmt(); // Read char statement
                break;
            case READLN:
                parseReadLnStmt();   // Read line statement
                break;
            case WRITEINT:
                parseWriteStmt();    // Write integer statement
                break;
            case WRITEREAL:
                parseWriteRealStmt(); // Write real statement
                break;
            case WRITECHAR:
                parseWriteCharStmt(); // Write char statement
                break;
            case WRITELN:
                parseWriteLnStmt();   // Write line statement
                break;
            case IF:
                parseIfStmt();  // If statement
                break;
            case CALL:
                parseCallStmt();      // Call statement
                break;
            case WHILE:
                parseWhileStmt();
                break;
            case LOOP:
                parseRepeatStmt();
                break;
            case MOD:
                match(TokenType.MOD);
                parseNameValue();
                break;
            case DIV:
                match(TokenType.DIV);
                parseNameValue();
                break;
            case EXIT:
                match(TokenType.EXIT);
                match(TokenType.SEMICOLON);
                break;
            default:
        }
    }

    private void parseAssStmt() {
        // Parsing an assignment statement: name := exp;
        parseName();
        match(TokenType.COLON_ASSIGNMENT_OPERATOR);
        parseExp();
    }
    private void parseExp() {
        parseTerm();
        while (currentToken.getType() == TokenType.INCREMENT_OPERATOR ||
                currentToken.getType() == TokenType.DECREMENT_OPERATOR ||
                currentToken.getType() == TokenType.MOD ||
                currentToken.getType() == TokenType.DIV ||
                currentToken.getType() == TokenType.DIVISION || currentToken.getType() == TokenType.MUL) {
            match(currentToken.getType());
            parseTerm();
        }
    }
    private void parseTerm() {
        parseFactor();
        while (currentToken.getType() == TokenType.ARITHMETIC_OPERATOR) {
            match(currentToken.getType());
            parseFactor();
        }
    }

    private void parseFactor() {
        if (currentToken.getType() == TokenType.LEFT_PAREN) {
            match(TokenType.LEFT_PAREN);
            parseExp();
            match(TokenType.RIGHT_PAREN);
        } else if (currentToken.getType() == TokenType.NAME || currentToken.getType() == TokenType.CONSTANT) {
            match(currentToken.getType());
        } else {
            error("Unexpected token in factor: " + currentToken.getType());
        }
    }
    private void parseReadIntStmt() {
        // Parsing a read statement: readint(name);
        match(TokenType.READINT);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }
    private void parseReadRealStmt() {
        // Parsing a read statement: readreal(name);
        match(TokenType.READREAL);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }
    private void parseReadCharStmt() {
        // Parsing a read statement: readchar(name);
        match(TokenType.READCHAR);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }
    private void parseReadLnStmt() {
        // Parsing a read statement: readln;
        match(TokenType.READLN);
    }
    private void parseWriteRealStmt() {
        // Parsing a write statement: writereal(name);
        match(TokenType.WRITEREAL);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }
    private void parseWriteCharStmt() {
        // Parsing a write statement: writechar(name);
        match(TokenType.WRITECHAR);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }
    private void parseWriteLnStmt() {
        // Parsing a write statement: writeln;
        match(TokenType.WRITELN);
    }
    private void parseWriteStmt() {

        match(TokenType.WRITEINT);
        match(TokenType.LEFT_PAREN);
        parseList();
        match(TokenType.RIGHT_PAREN);
    }

    private void parseIfStmt() {
        match(TokenType.IF);
        parseCondition();
        match(TokenType.THEN);
        parseStmtList();
        parseElseIfPart();
        parseElsePart();
        match(TokenType.END);
    }
    private void parseElseIfPart() {
        while (currentToken.getType() == TokenType.ELSEIF) {
            match(TokenType.ELSEIF);
            parseCondition();
            match(TokenType.THEN);
            parseStmtList();
        }
    }
    private void parseElsePart() {
        if (currentToken.getType() == TokenType.ELSE) {
            match(TokenType.ELSE);
            parseStmtList();
        }
    }
    private void parseCondition() {
        parseNameValue();
        parseRelationalOper();
        parseNameValue();
    }

    private void parseWhileStmt() {
        match(TokenType.WHILE);
        parseCondition();
        match(TokenType.DO);
        parseStmtList();
        match(TokenType.END);
    }
    private void parseRepeatStmt() {
        match(TokenType.LOOP);
        parseStmtList();
        match(TokenType.UNTIL);
        parseCondition();
    }

    private void parseCallStmt() {
        match(TokenType.CALL);
        parseName();
    }

    private void parseNameValue() {
        if (currentToken.getType() == TokenType.NAME || currentToken.getType() == TokenType.CONSTANT) {
            match(currentToken.getType());
        } else {
            error("Expected NAME or CONSTANT in condition.");
        }
    }

    private void parseRelationalOper() {
        switch (currentToken.getType()) {
            case EQUALITY_OPERATOR:
            case NOT_EQUAL:
            case LESS_THAN:
            case LESS_THAN_OR_EQUAL:
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
                match(currentToken.getType());
                break;
            default:
                error("Expected a relational operator in condition.");
        }
    }

    private void parseList() {
        parseName();
        while (currentToken.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            parseName();
        }
    }

    private void match(TokenType expectedType) {
        if (currentToken.getType() == expectedType) {
            currentToken = lexer.scan();
        } else {
            error("Unexpected token: " + currentToken.getLexeme()+ ". Expected: " + expectedType);
        }
    }

    private void error(String message) {
        System.out.println("Error: " + message + " at line " + currentToken.getLineNumber());
        System.exit(1);
    }
}