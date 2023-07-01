package com.seesaw.auth;

import com.seesaw.validator.PasswordConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

@Data
@PasswordConfirmation(
        password = "password",
        confirmPassword = "confirmPassword"
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmPassword {
        @NotEmpty
        private String password;
        @NotEmpty
        private String confirmPassword;
}
