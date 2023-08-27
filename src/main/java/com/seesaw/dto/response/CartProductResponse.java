package com.seesaw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductResponse {
    private String id;
    private String name;
    private String image;
    private double price;
    private int quantity;
}