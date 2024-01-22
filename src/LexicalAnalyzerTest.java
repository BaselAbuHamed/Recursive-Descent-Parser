import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LexicalAnalyzerTest {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("TestingFiles/test1.txt"))) {
            Parser parser = new Parser(reader);
            parser.parseModuleDecl();
            System.out.println("Parsing completed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}