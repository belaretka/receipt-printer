package builder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DirectorTest {

    Builder builder;
    Director director;
    List<String> data;
    String[] args;
    DecimalFormat df;

    @BeforeEach
    void setUp() {
        builder = new ReceiptBuilder();
        director = new Director(builder);
        data = Arrays.asList(
                "Walmart",
                "card1234,card1112,card2341",
                "1, Bread, 10.90, false",
                "2, Apples, 11.78, true",
                "3, Butter, 15.44, false",
                "4, 10 eggs, 14.98, false",
                "5, Milk, 12.48, false",
                "6, Potato chips, 13.78, true"
        );
        df = new DecimalFormat(".##");
    }

    @Test
    void constructReceiptTotalWith1ItemWithoutDiscount() {
        args = new String[]{"1-5", "card1234"};
        director.construct(args);
        double expected = 63.77;
        assertEquals(df.format(expected), df.format(director.getBuilder().getResult().getTotal()));
    }

    @Test
    void constructReceiptTotalWith1ItemWithDiscount() {
        args = new String[]{"2-10", "card1234"};
        director.construct(args);
        double expected = 11.78 * 10 * 0.9 * 1.17;
        assertEquals(df.format(expected), df.format(director.getBuilder().getResult().getTotal()));
    }

    @Test
    void constructFromFileReceiptTotalWithoutDiscount() {
        args = new String[]{"data.txt", "1-5", "2-10",  "card1234"};
        director.construct(args);
        double expected = 187.81d;
        assertEquals(df.format(expected), df.format(director.getBuilder().getResult().getTotal()));
    }

    @Test
    void constructingReceiptWithItemIdNotExistingShouldThrowException() {
        args = new String[]{"data.txt", "0-5", "2-10",  "card1234"};
        assertThrows(NoSuchElementException.class, () -> director.construct(args));
    }

    @Test
    void constructFromFileReceiptTotalWith1ofItemsWithDiscount() {
        args = new String[]{"data.txt", "1-5", "2-10", "6-6",  "card1234"};
        director.construct(args);
        double expected = 274.87d;
        assertEquals(df.format(expected), df.format(director.getBuilder().getResult().getTotal()));
    }

    @Test
    void setRegisteredDiscountCards() {
        director.setDiscountCards(data.get(1));
        assertLinesMatch(Arrays.asList("card1234","card1112","card2341"), director.getCards());
    }

    @Test
    void setStoreNameTakingFromData() {
        director.setStoreName(data.get(0));
        assertEquals(data.get(0), director.getStoreName());
    }

    @Test
    void initiateDataWithDefaultValue() {
        assertLinesMatch(data, director.initiateData());
    }
}