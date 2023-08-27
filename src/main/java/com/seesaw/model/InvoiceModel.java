package com.seesaw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
@Data
public class InvoiceModel {
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
    @ManyToOne(targetEntity = OrderModel.class)
    @JoinColumn(name = "order_id")
    private OrderModel orders;

    @ManyToOne(targetEntity = ProductModel.class)
    @JoinColumn(name = "product_id")
    private ProductModel products;
}
