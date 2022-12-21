package builder;

import model.Product;
import model.ReceiptItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptBuilderTest {

    protected static final double TAX_RATE = 17.0d/100;
    ReceiptItem item1, item2, item3 = null;
    Builder builder = null;
    String storeName, registeredDiscountCard, notRegisteredDiscountCard;

    @BeforeEach
    void setUp() {
        builder = new ReceiptBuilder();

        storeName = "Walmart";

        item1 = new ReceiptItem(new Product(1, "test1", 10.50, false), 4);
        item2 = new ReceiptItem(new Product(2, "test2", 100.0, true), 2);
        item3 = new ReceiptItem(new Product(3, "test3", 15.0, true), 10, 10d/100);

        registeredDiscountCard = "card1234";
        notRegisteredDiscountCard = "";
    }

    @Test
    void addTwoReceiptItemToReceiptItemsList() {
        assertEquals(Arrays.asList(item1, item2), builder.setReceiptItem(item1).setReceiptItem(item2).getResult().getReceiptItems());
    }

    @Test
    void storeNameShouldBeWalmart(){
        assertEquals("Walmart", builder.setStore(storeName).getResult().getStore());
    }

    @Test
    void storeNameShouldNotBeNull(){
        assertEquals("Store name", builder.setStore("").getResult().getStore());
    }

    @Test
    void taxableTotalOfOneItemWithoutDiscountShouldBePricePerUnitMultiplyByQty() {
        assertEquals(42.00, builder.setReceiptItem(item1).setTaxableTotal().getResult().getTaxableTotal());
    }

    @Test
    void taxableTotalOfTwoItemsWhereOneisPromotionalButQtyLessThan5ShouldBePricePerUnitMultiplyByQty() {
        assertEquals(242.00, builder.setReceiptItem(item1).setReceiptItem(item2).setTaxableTotal().getResult().getTaxableTotal());
    }

    @Test
    void taxableTotalOfThreeItemsWhereOneisPromotionalAndQtyMoreThan5ShouldBePricePerUnitMultiplyByQtyMinusDiscountSum() {
        assertEquals(10.50*4 + 100.00*2 + 15.00*10 - 15.00*10*0.1, builder.setReceiptItem(item1).setReceiptItem(item2).setReceiptItem(item3).setTaxableTotal().getResult().getTaxableTotal());
    }

    @Test
    void totalDiscountShouldBePricePerUnitByQtyByDiscountRate() {
        assertEquals(15.00*10*0.1, builder.setReceiptItem(item1).setReceiptItem(item2).setReceiptItem(item3).setDiscountCard(registeredDiscountCard).setTotalDiscount().getResult().getTotalDiscount());
    }

    @Test
    void totalDiscountShouldBeZeroWhenNoDiscountCard() {
        assertEquals(0d, builder.setReceiptItem(item1).setReceiptItem(item2).setReceiptItem(item3).setTotalDiscount().getResult().getTotalDiscount());
    }

    @Test
    void workOutTaxedSumWithDiscountCardApplied() {
        builder.setReceiptItem(item3).setDiscountCard(registeredDiscountCard).setTaxableTotal().setTotalDiscount();
        Double expected = (15d*10 - 15d*10*0.1)*TAX_RATE;
        assertEquals(expected, builder.setTaxedSum(TAX_RATE).getResult().getTaxedSum());
    }

    @Test
    void workOutTaxedSumWithoutDiscountCardApplied() {
        builder.setReceiptItem(item1).setReceiptItem(item2).setTaxableTotal().setTotalDiscount();
        Double expected = (10.50*4 + 100.00*2)*TAX_RATE;
        assertEquals(expected, builder.setTaxedSum(TAX_RATE).getResult().getTaxedSum());
    }

    @Test
    void discountCardShouldSetAsRegisteredCard() {
        assertEquals("card1234", builder.setDiscountCard(registeredDiscountCard).getResult().getDiscountCard());
    }

    @Test
    void discountCardShouldSetAsNullCard() {
        assertEquals("", builder.setDiscountCard(notRegisteredDiscountCard).getResult().getDiscountCard());
    }

    @Test
    void dateTimeShouldBeNow() {
        assertEquals(LocalDateTime.now(), builder.setDateTime(LocalDateTime.now()).getResult().getDateTime());
    }

}