package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
class InvoiceKey implements Serializable {
    @Column(name = "id_order")
    String orderId;
    @Column(name = "id_product")
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

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "id_order")
    private OrderModel orders;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "id_product")
    private ProductModel products;

    private Integer quantity;
    private Float price;

}
