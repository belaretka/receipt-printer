package builder;

import model.Receipt;
import model.ReceiptItem;

import java.time.LocalDateTime;

public interface Builder {

    void setReceiptItem(ReceiptItem item);

    void setStore(String store);

    void setDiscountCard(String str);

    void setDateTime(LocalDateTime dateTime);

    void setTaxableTotal();

    void setTotalDiscount();

    void setTaxedSum(double taxRate);

    Receipt getResult();
}
