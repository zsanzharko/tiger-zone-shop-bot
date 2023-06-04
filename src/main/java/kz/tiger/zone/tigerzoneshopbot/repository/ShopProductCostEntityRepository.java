package kz.tiger.zone.tigerzoneshopbot.repository;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopProductCostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopProductCostEntityRepository extends JpaRepository<ShopProductCostEntity, Integer> {
}
