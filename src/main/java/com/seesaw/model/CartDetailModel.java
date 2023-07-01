package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_detail")
@Data
public class CartDetailModel {
    @EmbeddedId
    private CartDetailKey id;

    @ManyToOne
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private CartModel carts;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductModel products;

    private Float price;
    private Integer quantity;
}
