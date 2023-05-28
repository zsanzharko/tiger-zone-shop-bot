package kz.tiger.zone.tigerzoneshopbot.service.category;

import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;

import java.util.List;

public interface ShopCategoryService {

    List<CategoryModel> getAllEnabledCategories();
}
