package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_detail")
@Data
public class CartDetailModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(
            name = "id",
            columnDefinition = "VARCHAR(255)"
    )
    private String id;
    private int quantity;
    private Double price;
    @ManyToOne(targetEntity = CartModel.class)
    @JoinColumn(name = "cart_id")
    private CartModel carts;

    @ManyToOne(targetEntity = ProductModel.class)
    @JoinColumn(name = "product_id")
    private ProductModel products;
}
