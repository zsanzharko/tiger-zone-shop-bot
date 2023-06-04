package kz.tiger.zone.tigerzoneshopbot.service.category;

import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import kz.tiger.zone.tigerzoneshopbot.model.ProductModel;

import java.util.List;

public interface ShopCategoryService {

    CategoryModel getCategory(Integer categoryId);

    List<CategoryModel> getAllEnabledCategories();

    List<ProductModel> getAllEnabledItems(Integer categoryId);

    ProductModel getProduct(Integer productId);
}
