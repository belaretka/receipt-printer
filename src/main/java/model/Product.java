package model;

public class Product {
    protected final Integer id;
    protected final String productName;
    protected final Double pricePerUnit;
    protected final boolean isPromotional;

    public Product(Integer id, String productName, Double pricePerUnit, boolean isPromotional) {
        this.id = id;
        this.productName = productName;
        this.pricePerUnit = pricePerUnit;
        this.isPromotional = isPromotional;
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

    public static Product parseProduct(String[] product) {
        return new Product(
                Integer.parseInt(product[0]),
                product[1],
                Double.parseDouble(product[2]),
                Boolean.parseBoolean(product[3]));
    }
}
