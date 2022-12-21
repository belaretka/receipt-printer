package model;

import java.time.LocalDateTime;
import java.util.List;

public class Receipt {
    protected final String store;
    protected final LocalDateTime dateTime;
    protected final List<ReceiptItem> receiptItems;
    protected final String discountCard;
    protected final Double taxableTotal;
    protected final Double totalDiscount;
    protected final Double taxedSum;

    public Receipt(String store, LocalDateTime dateTime, List<ReceiptItem> receiptItems, String discountCard, Double taxableTotal, Double totalDiscount, Double taxedSum) {
        this.store = store;
        this.dateTime = dateTime;
        this.receiptItems = receiptItems;
        this.discountCard = discountCard;
        this.taxableTotal = taxableTotal;
        this.totalDiscount = totalDiscount;
        this.taxedSum = taxedSum;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public String getStore() {
        return store;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Double getTaxableTotal() {
        return taxableTotal;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public Double getTaxedSum() {
        return taxedSum;
    }

    public String getDiscountCard() {
        return discountCard;
    }

    @Override
    public String toString() {
        return "Receipt{" + " receiptItems=" + receiptItems + "}\n";
    }

    public Double getTotal() {
        return receiptItems.stream()
                .map(ReceiptItem::getPrice).reduce(0d, Double::sum) - totalDiscount + taxedSum;
    }
}
