import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class LexicalAnalyzer {

    private char currentChar;
    private StringBuffer currentSpelling;

    private final BufferedReader inFile;
    private static int lineNumber = 1;
    private int position;

    public LexicalAnalyzer(BufferedReader inFile) {
        this.inFile = inFile;
        this.position = 1;
        currentSpelling = new StringBuffer();
        currentChar = readChar();
    }

    private char readChar() {
        try {
            int i = inFile.read();
            if (i == -1) {
                // End of file, return a special character to indicate the end
                return 0;
            } else {
                return (char) i;
            }
        } catch (IOException e) {
            System.out.println(e);
            return 0;
        }
    }

    private void takeIt() {
        currentSpelling.append(currentChar);
        currentChar = readChar();
    }

    private void skipIt() {
        currentChar = readChar();
    }

    public Token scan() {
        skipWhitespace();

        currentSpelling = new StringBuffer();

        TokenType kind = switch (currentChar) {

            case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ->
                    scanIdentifier();

            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> scanConstant();

            default -> scanOperator();
        };

        return new Token(currentSpelling.toString(), kind, lineNumber, position);
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(currentChar)) {
            if (currentChar == '\n') {
                lineNumber++;
                position = 1;
            } else {
                position++;
            }
            skipIt();
        }
    }

    private TokenType scanIdentifier() {
        while (Character.isLetter(currentChar)) {
            takeIt();
        }

        String identifier = currentSpelling.toString();
        TokenType reservedWordType = ReservedWord.getTokenType(identifier);

        if (reservedWordType == TokenType.KEYWORD) {
            return reservedWordType;
        } else {
            // Check for additional reserved words
            if (identifier.equalsIgnoreCase("MODULE")) {
                return TokenType.MODULE;
            }else if (identifier.equalsIgnoreCase("PROCEDURE")) {
                return TokenType.PROCEDURE;
            } else if (identifier.equalsIgnoreCase("BEGIN")) {
                return TokenType.BEGIN;
            } else if (identifier.equalsIgnoreCase("END")) {
                return TokenType.END;
            } else if(identifier.equalsIgnoreCase("CONST")) {
                return TokenType.CONST;
            } else if(identifier.equalsIgnoreCase("VAR")) {
                return TokenType.VAR;
            } else if(identifier.equalsIgnoreCase("INTEGER")) {
                return TokenType.INTEGER;
            } else if(identifier.equalsIgnoreCase("REAL")) {
                return TokenType.REAL;
            } else if(identifier.equalsIgnoreCase("CHAR")) {
                return TokenType.CHAR;
            }else if(identifier.equalsIgnoreCase("READINT")) {
                return TokenType.READINT;
            }else if(identifier.equalsIgnoreCase("READREAL")) {
                return TokenType.READREAL;
            } else if(identifier.equalsIgnoreCase("READCHAR")) {
                return TokenType.READCHAR;
            } else if(identifier.equalsIgnoreCase("READLN")) {
                return TokenType.READLN;
            } else if(identifier.equalsIgnoreCase("WRITEINT")) {
                return TokenType.WRITEINT;
            } else if(identifier.equalsIgnoreCase("WRITEREAL")) {
                return TokenType.WRITEREAL;
            } else if(identifier.equalsIgnoreCase("WRITECHAR")) {
                return TokenType.WRITECHAR;
            } else if(identifier.equalsIgnoreCase("WRITELN")) {
                return TokenType.WRITELN;
            } else if (identifier.equalsIgnoreCase("IF")) {
                return TokenType.IF;
            } else if (identifier.equalsIgnoreCase("THEN")) {
                return TokenType.THEN;
            } else if (identifier.equalsIgnoreCase("ELSEIF")) {
                return TokenType.ELSEIF;
            } else if (identifier.equalsIgnoreCase("ELSE")) {
                return TokenType.ELSE;
            } else if (identifier.equalsIgnoreCase("WHILE")) {
                return TokenType.WHILE;
            } else if (identifier.equalsIgnoreCase("DO")) {
                return TokenType.DO;
            } else if (identifier.equalsIgnoreCase("LOOP")) {
                return TokenType.LOOP;
            } else if (identifier.equalsIgnoreCase("UNTIL")) {
                return TokenType.UNTIL;
            } else if (identifier.equalsIgnoreCase("EXIT")) {
                return TokenType.EXIT;
            } else if (identifier.equalsIgnoreCase("CALL")) {
                return TokenType.CALL;
            } else if (identifier.equalsIgnoreCase("MOD")) {
                return TokenType.MOD;
            } else if (identifier.equalsIgnoreCase("DIV")) {
                return TokenType.DIV;
            } else if (identifier.equalsIgnoreCase("NOT_EQUAL")) {
                return TokenType.NOT_EQUAL;
            } else {
                return TokenType.NAME;
            }
        }
    }

    private TokenType scanConstant() {
        while (Character.isDigit(currentChar) || currentChar == '.') {
            takeIt();
        }
        return TokenType.CONSTANT;
    }

    private TokenType scanOperator() {

        char currentOperator = currentChar;

        takeIt();

        switch (currentOperator) {
            case '=':
                if (currentChar == '=') {
                    takeIt();
                    return TokenType.EQUALITY_OPERATOR;
                }else {
                    return TokenType.ASSIGNMENT_OPERATOR;
                }

            case '<':
                if (currentChar == '=') {
                    takeIt();
                    return TokenType.LESS_THAN_OR_EQUAL;
                } else {
                    return TokenType.LESS_THAN;
                }

            case '>':
                if (currentChar == '=') {
                    takeIt();
                    return TokenType.GREATER_THAN_OR_EQUAL;
                } else {
                    return TokenType.GREATER_THAN;
                }
            case '|':
                if (currentChar == '=') {
                    takeIt();
                    return TokenType.NOT_EQUAL;
                } else {
                    return TokenType.ERROR;
                }

            case '+':
                if (currentChar == '+') {
                    takeIt();
                    return TokenType.INCREMENT_OPERATOR;
                } else {
                    return TokenType.ARITHMETIC_OPERATOR;
                }

            case '-':
                if (currentChar == '-') {
                    takeIt();
                    return TokenType.DECREMENT_OPERATOR;
                } else {
                    return TokenType.ARITHMETIC_OPERATOR;
                }

            case '*':
                return TokenType.MUL;
            case '/':
                return TokenType.DIVISION;
            case '(':
                return TokenType.LEFT_PAREN;

            case ')':
                return TokenType.RIGHT_PAREN;

            case ':':
                if (currentChar == '=') {
                    takeIt();
                    return TokenType.COLON_ASSIGNMENT_OPERATOR;
                }else {
                    return TokenType.COLON;
                }
            case ',':
                return TokenType.COMMA;

            case ';':
                return TokenType.SEMICOLON;
            case '.':
                return TokenType.EOF;
            default:
                return TokenType.ERROR;
        }
    }
}