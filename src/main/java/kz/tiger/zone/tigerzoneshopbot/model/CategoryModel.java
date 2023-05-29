package kz.tiger.zone.tigerzoneshopbot.model;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopCategoryEntity;
import lombok.Data;

@Data
public class CategoryModel implements ShopModel {
    private Integer id;
    private String title;
    private String description;

    public static CategoryModel transform(ShopCategoryEntity entity) {
        CategoryModel model = new CategoryModel();
        model.id = entity.getId();
        model.title = entity.getTitle();
        model.description = entity.getDescription();
        return model;
    }
}
