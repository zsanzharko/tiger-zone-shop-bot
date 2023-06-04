package kz.tiger.zone.tigerzoneshopbot.service.shop;

import kz.tiger.zone.tigerzoneshopbot.anypay.Currency;
import kz.tiger.zone.tigerzoneshopbot.entity.ShopCategoryEntity;
import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import kz.tiger.zone.tigerzoneshopbot.model.ProductModel;
import kz.tiger.zone.tigerzoneshopbot.repository.ShopCategoryEntityRepository;
import kz.tiger.zone.tigerzoneshopbot.repository.ShopItemEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Configuration
public class ShopServiceImpl implements ShopCategoryService, ShopProductService, ShopCurrencyService {
    private final ShopCategoryEntityRepository categoryRepository;
    private final ShopItemEntityRepository productRepository;

    @Value("${shop.currency}")
    private Currency currency;
    @Override
    public CategoryModel getCategory(Integer categoryId) {
        var category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            return CategoryModel.transform(category.get());
        }
        throw new EntityNotFoundException();
    }

    @Override
    public List<CategoryModel> getAllEnabledCategories() {
        List<ShopCategoryEntity> categoryList = categoryRepository.findAllByIsEnabledIsTrue();
        if (categoryList == null) {
            return new ArrayList<>();
        }
        return categoryList.stream()
                .map(CategoryModel::transform)
                .toList();
    }

    @Override
    public List<ProductModel> getAllEnabledItems(Integer categoryId) {
        ShopCategoryEntity category = categoryRepository.findByIdAndIsEnabledIsTrue(categoryId);

        if (category == null) {
            return new ArrayList<>();
        }
        return category.getSubItemEntityList().stream()
                .map((product) -> ProductModel.transform(product, currency))
                .toList();
    }

    @Override
    public ProductModel getProduct(Integer productId) {
        var product = productRepository.findById(productId);
        return product
                .map((p) -> ProductModel.transform(p, currency))
                .orElseThrow(EntityNotFoundException::new);
    }
}
