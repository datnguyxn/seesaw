package com.seesaw.auth;

import com.seesaw.model.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

   private String firstname;
   private String lastname;

    @Email(message = "Email is invalid")
    @NotEmpty(message = "Email is required")
    private String email;
//
    @NotEmpty(message = "Password is required")
    @Min(value = 8, message = "Password must be at least 8 characters")
    private String password;

    private String gender;

    private String contact;

    private String avatar;

}
