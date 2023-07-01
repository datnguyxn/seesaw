package com.seesaw.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddInfoRequest {
    @Null
    private String firstName;
    @Null
    private String lastName;
    @NotEmpty
    private String email;
    @Null
    private String phone;
    @NotEmpty
    private String address;
    @Null
    private String zipCode;
    @Null
    private String optional;
}
