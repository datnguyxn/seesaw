package com.seesaw.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddInvoiceRequest {
    private String product_id;
    private String order_id;
    private int quantity;
}
