package kz.tiger.zone.tigerzoneshopbot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product", schema = "shop")
@Getter
@Setter
public class ShopProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "image_url", length = 2500)
    private String imageUrl;
    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    private ShopCategoryEntity item;
    @Column(name = "title")
    private String title;
    @Column(name = "description", length = 1024)
    private String description;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "product",
            orphanRemoval = true)
    private List<ShopProductCostEntity> productCostList;
    @Column(name = "enabled")
    private Boolean isEnabled;
}
