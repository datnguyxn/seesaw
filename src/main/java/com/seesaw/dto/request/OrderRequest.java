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
public class OrderRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private Float total_amount;
    @NotEmpty
    private String user_id;
}
