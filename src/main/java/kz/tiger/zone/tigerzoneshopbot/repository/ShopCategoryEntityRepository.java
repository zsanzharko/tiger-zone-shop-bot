package kz.tiger.zone.tigerzoneshopbot.repository;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopCategoryEntityRepository extends JpaRepository<ShopCategoryEntity, Integer> {

    List<ShopCategoryEntity> findAllByIsEnabledIsTrue();

    ShopCategoryEntity findByIdAndIsEnabledIsTrue(Integer categoryId);
}
