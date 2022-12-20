import builder.Builder;
import builder.Director;
import builder.ReceiptBuilder;
import utils.ReceiptPrinter;

import java.io.IOException;

public class ReceiptMain {
    public static void main(String[] args) {
        Builder builder = null;
        try {
            builder = new ReceiptBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Director director = new Director(builder);
        director.construct(args);
        assert builder != null;
        ReceiptPrinter printer = new ReceiptPrinter(builder.getResult());
        printer.printToConsole();
        try {
            printer.printToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
