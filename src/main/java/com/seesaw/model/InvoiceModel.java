package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
class InvoiceKey implements Serializable {
    @Column(name = "order_id")
    String orderId;
    @Column(name = "product_id")
    String productId;
}
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
@Data
public class InvoiceModel {
    @EmbeddedId
    private InvoiceKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderModel orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductModel products;

    private Integer quantity;
    private Float price;

}
