package com.seesaw.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Float total_amount;
    private String status;
}
