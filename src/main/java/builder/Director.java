package builder;

import model.Product;
import model.ReceiptItem;
import utils.FileReader;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Director {

    protected static final double DISCOUNT = 10.0d/100;
    protected static final double TAX_RATE = 17.0d/100;

    private final Builder builder;

    private List<String> cards;
    private final Map<Integer, Product> goods;
    private String storeName;

    public Director(Builder builder) {
        this.builder = builder;
        this.goods = new HashMap<>();
    }

    public void construct(String[] args) {

        List<String> data = args[0].contains(".txt") ? new FileReader(args[0]).read() : initiateData();

        setUpData(data);

        builder.setStore(storeName);

        Optional<String> discountCard = Arrays.stream(args).filter(str -> str.contains("card")).findAny();
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
            int qty = Integer.parseInt(item[1]);
            int itemId = Integer.parseInt(item[0]);
            if(!goods.containsKey(itemId)){
                throw new NoSuchElementException("no product with id:" + itemId);
            }
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

    private void setUpData(List<String> data) {
        storeName = data.get(0);

        cards = Arrays.stream(data.get(1).split(",")).collect(Collectors.toList());

        data.stream()
                .skip(2)
                .map(str -> str.split(", "))
                .forEach(strings -> goods.put(Integer.parseInt(strings[0]), Product.parseProduct(strings)));
    }

    private List<String> initiateData() {
        return Arrays.asList(
                "Walmart",
                "card1234,card1112,card2341",
                "1, Bread, 10.90, false",
                "2, Apples, 11.78, true",
                "3, Butter, 15.44, false",
                "4, 10 eggs, 14.98, false",
                "5, Milk, 12.48, false",
                "6, Potato chips, 13.78, true"
        );
    }
}
