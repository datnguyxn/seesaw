package com.seesaw.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String gender;
    private String contact;
    private String address;
}
