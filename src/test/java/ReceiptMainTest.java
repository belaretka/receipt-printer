import model.Receipt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptMainTest {

    String[] args;

    @Test
    void mainMethodWithNoArgumentShouldThrowException() {
        args = new String[]{};
        assertThrows(IllegalArgumentException.class, () -> ReceiptMain.main(args));
    }

    @Test
    void mainMethodWithArgumentsWithoutFileReading(){
        args = new String[]{"1-5", "2-10", "card1234"};
        assertAll(() -> ReceiptMain.main(args));
    }

    @Test
    void mainMethodWithArgumentsWithFileReading(){
        args = new String[]{"data.txt","1-5", "2-10", "card1234"};
        assertAll(() -> ReceiptMain.main(args));
    }
}