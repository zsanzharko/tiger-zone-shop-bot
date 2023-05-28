package kz.tiger.zone.tigerzoneshopbot.service.category;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopCategoryEntity;
import kz.tiger.zone.tigerzoneshopbot.model.CategoryModel;
import kz.tiger.zone.tigerzoneshopbot.repository.ShopCategoryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopCategoryServiceImpl implements ShopCategoryService {
    private final ShopCategoryEntityRepository categoryRepository;

    @Override
    public List<CategoryModel> getAllEnabledCategories() {
        List<ShopCategoryEntity> categoryList = categoryRepository.findAllByIsEnabled(true);
        return categoryList.stream()
                .map(CategoryModel::transform)
                .toList();
    }
}
