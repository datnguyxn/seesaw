package com.seesaw.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPaymentRequest {
    @NotEmpty(message = "Cart name is required")
    private String cardName;
//    @NotEmpty(message = "Card number is required")
    private String cardNumber;
    @NotEmpty(message = "Month is required")
    private String month;
    @NotEmpty(message = "Year is required")
    private String year;
    @NotEmpty(message = "CVV is required")
    private String cvv;
}
