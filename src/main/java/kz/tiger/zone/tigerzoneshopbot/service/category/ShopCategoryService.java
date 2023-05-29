package kz.tiger.zone.tigerzoneshopbot.service.category;

import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import kz.tiger.zone.tigerzoneshopbot.model.ItemModel;

import java.util.List;

public interface ShopCategoryService {

    List<CategoryModel> getAllEnabledCategories();

    List<ItemModel> getAllEnabledItems(Integer categoryId);
}
