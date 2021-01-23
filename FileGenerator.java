package original;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;



public class FileGenerator {

    private FileWriter fw;
    protected PrintWriter pw;

    public FileGenerator(String fileName) {
        try {
            fw = new FileWriter(fileName);
            pw = new PrintWriter(fw, true);
        } catch (Exception e) {
            System.out.println("couldn't open file");
        }
    }

    public void addLine(String line) {
        pw.println(line);
    }

    public void addLines(List<String> lines) {
        for (String line: lines) {
        pw.println(line);
        }
    }

    public void finish() {
        try {
            fw.close();
        }
        catch (Exception e) {
            System.out.println("Couldn't close file");
        }
    }
}
