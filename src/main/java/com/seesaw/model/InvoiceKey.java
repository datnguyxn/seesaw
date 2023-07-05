package com.seesaw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceKey implements Serializable {
    @Column(name = "order_id")
    String orderId;
    @Column(name = "product_id")
    String productId;
}
