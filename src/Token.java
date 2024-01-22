public class Token {
    private String lexeme; // The actual string that was matched
    private TokenType type; // The type of token that was matched
    private int lineNumber; // The line number where the token was matched
    private int position;// The position in the line where the token was matched



    public Token(String lexeme, TokenType type, int lineNumber, int position) {
        this.lexeme = lexeme;
        this.type = type;
        this.lineNumber = lineNumber;
        this.position = position;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "(" + lexeme + ", " + type + ") at line " + lineNumber + ", position " + position;
    }
}