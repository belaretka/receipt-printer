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
    public Builder setReceiptItem(ReceiptItem item) {
        receiptItems.add(item);
        return this;
    }

    @Override
    public Builder setStore(String s) {
        this.store = !s.isEmpty() ? s : "Store name";
        return this;
    }

    @Override
    public Builder setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public Builder setTaxableTotal() {
        this.taxableTotal = receiptItems.stream()
                .map(ReceiptItem::getTotal).reduce(0d, Double::sum) - totalDiscount;
        return this;
    }

    @Override
    public Builder setTotalDiscount() {
        this.totalDiscount = !discountCard.isEmpty() ? receiptItems.stream()
                .map(ReceiptItem::getDiscount).reduce(0d, Double::sum) : 0d;
        return this;
    }

    @Override
    public Builder setTaxedSum(final double tax) {
        this.taxedSum = this.taxableTotal * tax;
        return this;
    }

    @Override
    public Builder setDiscountCard(String str) {
        this.discountCard =
                !str.isEmpty() ? str : "";
        return this;
    }

    @Override
    public Receipt getResult() {
        return new Receipt(store, dateTime, receiptItems, discountCard, taxableTotal, totalDiscount, taxedSum);
    }
}
