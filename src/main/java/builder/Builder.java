package builder;

import model.Receipt;
import model.ReceiptItem;

import java.time.LocalDateTime;

public interface Builder {

    Builder setReceiptItem(ReceiptItem item);

    Builder setStore(String store);

    Builder setDiscountCard(String str);

    Builder setDateTime(LocalDateTime dateTime);

    Builder setTaxableTotal();

    Builder setTotalDiscount();

    Builder setTaxedSum(double taxRate);

    Receipt getResult();
}
