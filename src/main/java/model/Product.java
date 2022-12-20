package model;

public class Product {
    protected Integer id;
    protected String productName;
    protected Double pricePerUnit;
    protected boolean isPromotional;

    public Product(Integer id, String productName, Double pricePerUnit, boolean isPromotional) {
        this.id = id;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.isPromotional = isPromotional;
    }

    public Product(String productName, Double pricePerUnit, boolean isPromotional) {
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.isPromotional = isPromotional;
    }

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPricePerUnit() {
        return pricePerUnit;
    }

    public boolean isPromotional() {
        return isPromotional;
    }
}