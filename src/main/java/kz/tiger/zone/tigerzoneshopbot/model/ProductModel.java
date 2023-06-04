package kz.tiger.zone.tigerzoneshopbot.model;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopProductEntity;
import lombok.Data;

@Data
public class ProductModel implements ShopModel {
    private Integer id;
    private String title;
    private String imageUrl;
    private String description;

    public static ProductModel transform(ShopProductEntity product) {
        ProductModel model = new ProductModel();
        model.setId(product.getId());
        model.setTitle(product.getTitle());
        model.setImageUrl(product.getImageUrl());
        if (product.getDescription() == null)
            model.setDescription("");
        else
            model.setDescription(product.getDescription());
        return model;
    }
}
