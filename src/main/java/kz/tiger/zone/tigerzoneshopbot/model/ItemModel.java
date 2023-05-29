package kz.tiger.zone.tigerzoneshopbot.model;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopItemEntity;
import lombok.Data;

@Data
public class ItemModel implements ShopModel {
    private Integer id;
    private String title;
    private String description;

    public static ItemModel transform(ShopItemEntity product) {
        ItemModel model = new ItemModel();
        model.setId(product.getId());
        model.setTitle(product.getTitle());
        model.setDescription(product.getDescription());
        return model;
    }
}
