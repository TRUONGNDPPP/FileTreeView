package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileReader extends Reader {

    public FileReader() {

    }

    public FileReader(File file) {

    }

    public List<String> read(File file) {
        List<String> lines = new ArrayList<String>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((line = br.readLine()) != null) {
                    lines.add(line);
            }

            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return lines;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        return 0;
    }

    @Override
    public void close() throws IOException {
    }
}
