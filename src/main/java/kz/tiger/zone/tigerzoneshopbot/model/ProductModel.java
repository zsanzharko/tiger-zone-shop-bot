package kz.tiger.zone.tigerzoneshopbot.model;

import kz.tiger.zone.tigerzoneshopbot.anypay.Currency;
import kz.tiger.zone.tigerzoneshopbot.entity.ShopProductEntity;
import lombok.Data;

@Data
public class ProductModel implements ShopModel {
    private Integer id;
    private String title;
    private String imageUrl;
    private String description;
    private String cost;
    private String currency;

    public static ProductModel transform(ShopProductEntity product, Currency currency) {
        ProductModel model = new ProductModel();
        model.id = product.getId();
        model.title = product.getTitle();
        model.imageUrl = product.getImageUrl();
        if (product.getDescription() == null) model.description = "";
        else model.description = product.getDescription();

        var productCost = product.getProductCostList().stream()
                .filter(p -> p.getCurrency() == currency).findFirst();

        if (productCost.isPresent()) {
            String pCost = String.valueOf(productCost.get().getCost());
            int length = pCost.substring(pCost.indexOf('.') + 1).length();
            if (length <= 1) model.cost = pCost;
            else model.cost = pCost.substring(0, pCost.indexOf('.') + 1 + 2);

            switch (currency) {
                case KZT -> model.currency = "тг.";
                case USD -> model.currency = "$";
                case RUB -> model.currency = "руб.";
            }
        } else model.cost = "";
        return model;
    }
}
