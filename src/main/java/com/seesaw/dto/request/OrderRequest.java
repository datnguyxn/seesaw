package com.seesaw.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String status;
    @NotEmpty
    private String user_id;
    private List<OrderProduct> products;
}