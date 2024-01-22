import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SourceFile {
    public BufferedReader openFile(String fileName) throws FileNotFoundException {
        FileReader fr = new FileReader(fileName);
        return new BufferedReader(fr);
    }
}