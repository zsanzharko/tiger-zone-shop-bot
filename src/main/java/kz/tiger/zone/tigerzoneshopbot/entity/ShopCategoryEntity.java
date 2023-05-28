package kz.tiger.zone.tigerzoneshopbot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "category", schema = "shop")
@Getter
@Setter
public class ShopCategoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "description", length = 2500)
    private String description;
    @Column(name = "enabled")
    private Boolean isEnabled;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "item",
            orphanRemoval = true)
    List<ShopItemEntity> subItemEntityList;
}
