package model;

public class ReceiptItem {

    protected Product product;
    protected Integer quantity;
    protected Double price;
    protected Double discount = 0d;

    public ReceiptItem(Product product, Integer quantity, Double discount) {
        this.product = product;
        this.quantity = quantity;
        this.price = this.quantity * this.product.pricePerUnit;
        this.discount = this.price * discount;
    }

    public ReceiptItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = this.quantity * this.product.pricePerUnit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getDiscount() {
        return discount;
    }

    public String getProductName() {
        return product.getProductName();
    }

    public Double getProductPrice() {
        return product.getPricePerUnit();
    }

    public Double getTotal() {
        return discount == 0d ? price : price - discount;
    }
}
