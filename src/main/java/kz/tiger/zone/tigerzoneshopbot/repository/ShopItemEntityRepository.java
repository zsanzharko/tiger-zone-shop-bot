package kz.tiger.zone.tigerzoneshopbot.repository;

import kz.tiger.zone.tigerzoneshopbot.entity.ShopItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopItemEntityRepository extends JpaRepository<ShopItemEntity, Integer> {
}
