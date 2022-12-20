package builder;

import model.Receipt;
import model.ReceiptItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReceiptBuilder implements Builder{

    protected final List<ReceiptItem> receiptItems = new ArrayList<>();
    protected String store;
    protected LocalDateTime dateTime;
    protected Double taxableTotal;
    protected Double totalDiscount = 0d;
    protected Double taxedSum;
    protected String discountCard = "";

    @Override
    public void setReceiptItem(ReceiptItem item) {
        receiptItems.add(item);
    }

    @Override
    public void setStore(String s) {
        this.store = s;
    }

    @Override
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void setTaxableTotal() {
        this.taxableTotal = receiptItems.stream()
                .map(ReceiptItem::getTotal).reduce(0d, Double::sum);
    }

    @Override
    public void setTotalDiscount() {
        this.totalDiscount = receiptItems.stream()
                .map(ReceiptItem::getDiscount).reduce(0d, Double::sum) ;
    }

    @Override
    public void setTaxedSum(final double tax) {
        if(discountCard.isEmpty()) {
            this.taxedSum = (this.taxableTotal - this.totalDiscount) * tax;
        } else {
            this.taxedSum = this.taxableTotal * tax;
        }
    }

    @Override
    public void setDiscountCard(String str) {
        this.discountCard = str;
    }

    @Override
    public Receipt getResult() {
        return new Receipt(store, dateTime, receiptItems, discountCard, taxableTotal, totalDiscount, taxedSum);
    }
}
