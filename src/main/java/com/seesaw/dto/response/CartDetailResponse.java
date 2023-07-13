package com.seesaw.dto.response;

import com.seesaw.model.CartDetailKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailResponse {
    private CartDetailKey id;
    private String product;
    private Float price;
    private int quantity;
}
