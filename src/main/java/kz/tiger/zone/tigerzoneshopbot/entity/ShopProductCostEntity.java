package kz.tiger.zone.tigerzoneshopbot.entity;

import kz.tiger.zone.tigerzoneshopbot.anypay.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(schema = "shop", name = "product_cost")
@Getter
@Setter
public class ShopProductCostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ShopProductEntity product;
    @Column(name = "cost")
    private Double cost;
    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
