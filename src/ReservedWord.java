import java.util.HashMap;
import java.util.Map;

public class ReservedWord {
    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    static {
        KEYWORDS.put("module", TokenType.MODULE);
        KEYWORDS.put("const", TokenType.CONST);
        KEYWORDS.put("var", TokenType.VAR);
        KEYWORDS.put("procedure", TokenType.PROCEDURE);
        KEYWORDS.put("begin", TokenType.BEGIN);
        KEYWORDS.put("end", TokenType.END);
        KEYWORDS.put("integer", TokenType.INTEGER);
        KEYWORDS.put("real", TokenType.REAL);
        KEYWORDS.put("char", TokenType.CHAR);
        KEYWORDS.put("mod", TokenType.MOD);
        KEYWORDS.put("div", TokenType.DIV);
        KEYWORDS.put("readint", TokenType.READINT);
        KEYWORDS.put("readreal", TokenType.READREAL);
        KEYWORDS.put("readchar", TokenType.READCHAR);
        KEYWORDS.put("readln", TokenType.READLN);
        KEYWORDS.put("writeint", TokenType.WRITEINT);
        KEYWORDS.put("writereal", TokenType.WRITEREAL);
        KEYWORDS.put("writechar", TokenType.WRITECHAR);
        KEYWORDS.put("writeln", TokenType.WRITELN);
        KEYWORDS.put("if", TokenType.IF);
        KEYWORDS.put("then", TokenType.THEN);
        KEYWORDS.put("elseif", TokenType.ELSEIF);
        KEYWORDS.put("else", TokenType.ELSE);
        KEYWORDS.put("while", TokenType.WHILE);
        KEYWORDS.put("do", TokenType.DO);
        KEYWORDS.put("loop", TokenType.LOOP);
        KEYWORDS.put("until", TokenType.UNTIL);
        KEYWORDS.put("exit", TokenType.EXIT);
        KEYWORDS.put("call", TokenType.CALL);
    }

    public static TokenType getTokenType(String word) {
        return KEYWORDS.getOrDefault(word, TokenType.IDENTIFIER);
    }
}