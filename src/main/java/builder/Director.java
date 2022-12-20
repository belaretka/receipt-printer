package builder;

import model.Product;
import model.ReceiptItem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Director {

    protected static final double DISCOUNT = 10.0d/100;
    protected static final double TAX_RATE = 17.0d/100;

    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public void construct(String[] args) {

        String storeName = "Wallmart";

        Map<Integer, Product> goods = new HashMap<>();
        goods.put(1, new Product("Bread", 10.90, false));
        goods.put(2, new Product( "Apples", 11.78, true));
        goods.put(3, new Product("Butter", 15.44, false));
        goods.put(4, new Product( "10 eggs", 14.98, false));
        goods.put(5, new Product( "Milk", 12.48, false));
        goods.put(6, new Product( "Potato chips", 13.78, true));

        List<String> cards = Arrays.asList("c1234","c11121","c23411");

        builder.setStore(storeName);

        Optional<String> discountCard = Arrays.stream(args).filter(str -> str.contains("c")).findAny();

        boolean isActiveCard = false;

        if (discountCard.isPresent()) {
            if(cards.stream().anyMatch(card -> card.equals(discountCard.get()))) {
                builder.setDiscountCard(discountCard.get());
                isActiveCard = true;
            }
        }

        List<String> o = Arrays.stream(args)
                .filter(i -> i.contains("-"))
                .collect(Collectors.toList());

        for(String str : o) {
            String[] item = str.split("-");
            int itemId = Integer.parseInt(item[0]);
            int qty = Integer.parseInt(item[1]);

            //тут пробрасывает NullPointerException т.к в аргументах есть такое itemId которого нет
            if(isActiveCard && qty > 5 && goods.get(itemId).isPromotional()) {
                builder.setReceiptItem(new ReceiptItem(goods.get(itemId), qty, DISCOUNT));
            } else {
                builder.setReceiptItem(new ReceiptItem(goods.get(itemId), qty));
            }
        }

        builder.setTaxableTotal();
        if(isActiveCard) {
            builder.setTotalDiscount();
        }
        builder.setTaxedSum(TAX_RATE);
        builder.setDateTime(LocalDateTime.now());
    }
}
