package utils;

import model.Receipt;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

public class ReceiptPrinter {

    static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private final Receipt receipt;
    protected ByteArrayOutputStream baos;

    public ReceiptPrinter(Receipt receipt) {
        this.receipt = receipt;
        formReceipt();
    }

    public void formReceipt() {
        final String divider = "-----------------------------------------------------------";
        baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);
        System.out.printf("%35s\n", "CASH RECEIPT");
        System.out.printf("%33s\n", receipt.getStore());
        System.out.println(divider);
        System.out.printf("Date: %-30s\n", dtf.format(receipt.getDateTime()));
        System.out.println(divider);
        System.out.printf("%5s|%20s|%15s|%15s|\n", "qty", "description", "price", "total");
        receipt.getReceiptItems().forEach(item -> {
            System.out.printf("%5s %20s %15.2f %15.2f \n", item.getQuantity(), item.getProductName(), item.getProductPrice(), item.getPrice());
            if(!receipt.getDiscountCard().isEmpty() && item.getDiscount() > 0 ) {
                System.out.printf("%42s %15.2f\n", "discount", -item.getDiscount());
                System.out.printf("%42s %15.2f\n","total with discount", item.getTotal());
            }
        });
        System.out.println(divider);
        if(!receipt.getDiscountCard().isEmpty()) {
            System.out.printf("Total discount(%s): %32.2f\n",receipt.getDiscountCard(), -receipt.getTotalDiscount());
        }
        System.out.printf("Taxable total: %43.2f\n", receipt.getTaxableTotal());
        System.out.printf("VAT17: %51.2f\n", receipt.getTaxedSum());
        System.out.println(divider);
        System.out.printf("TOTAL: %51.2f\n", receipt.getTotal());
        System.out.println(divider);
        System.out.flush();
        System.setOut(old);
    }

    public void printToConsole() {
        System.out.println(baos);
    }

    public void printToFile() throws IOException {
        //String filename = "output/receipt " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss").format(receipt.getDateTime()) + ".txt";

        String filename = "output/receipt.txt";
        FileOutputStream fos = null;
        try {
            Path path = FileSystems.getDefault().getPath(filename);
            fos = new FileOutputStream(String.valueOf(path));
            baos.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert fos != null;
            fos.close();
        }
    }
}
