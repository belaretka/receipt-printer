import builder.Builder;
import builder.Director;
import builder.ReceiptBuilder;
import utils.ReceiptPrinter;

import java.io.IOException;

public class ReceiptMain {
    public static void main(String[] args) {

        if(args.length == 0) {
            throw new IllegalArgumentException("no argument was provided");
        }

        Builder builder = new ReceiptBuilder();
        Director director = new Director(builder);
        director.construct(args);
        ReceiptPrinter printer = new ReceiptPrinter(builder.getResult());
        printer.printToConsole();
        try {
            printer.printToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
