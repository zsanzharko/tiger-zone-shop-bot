package kz.tiger.zone.tigerzoneshopbot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product", schema = "shop")
@Getter
@Setter
public class ShopProductEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "image")
    private byte[] image;
    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    private ShopCategoryEntity item;
    @Column(name = "title")
    private String title;
    @Column(name = "description", length = 1024)
    private String description;
    @Column(name = "enabled")
    private Boolean isEnabled;
}
