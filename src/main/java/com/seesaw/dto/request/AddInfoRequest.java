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

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String zipCode;

    private String optional;
}
