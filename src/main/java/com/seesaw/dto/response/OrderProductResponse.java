package com.seesaw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductResponse {
    private String name;
    private String brand;
    private int quantity;
    private Double price;
}
