package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_detail")
@Data
public class CartDetailModel {
    @EmbeddedId
    private CartDetailKey id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("cartId")
    @JoinColumn(name = "cart_id")
    private CartModel carts;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductModel products;

    private Float price;
    private Integer quantity;
}
