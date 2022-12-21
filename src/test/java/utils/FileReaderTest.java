package utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void readFromProvidedFile() {
        Reader reader = new FileReader("data.txt");
        assertLinesMatch(Arrays.asList(
                "Walmart",
                "card1234,card1112,card2341",
                "1, Bread, 10.90, false",
                "2, Apples, 11.78, true",
                "3, Butter, 15.44, false",
                "4, 10 eggs, 14.98, false",
                "5, Milk, 12.48, false",
                "6, Potato chips, 13.78, true"
        ), reader.read());
    }
}