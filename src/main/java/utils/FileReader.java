package utils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader implements Reader {

    String filename;

    public FileReader(String filename) {
        this.filename = "src\\main\\resources\\" + filename;
    }

    @Override
    public List<String> read() {
        List<String> result = new ArrayList<>();
        Path path = Paths.get(filename);
        Scanner sc;
        try {
            sc = new Scanner(path);
            while (sc.hasNextLine()) {
                result.add(sc.nextLine());
            }
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
