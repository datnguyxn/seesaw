package com.seesaw.auth;

import com.seesaw.validator.PasswordConfirmation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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
public class PasswordRequest {
        @NotEmpty(message = "Password is required")
        @Min(value = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
        private String password;

        @NotEmpty(message = "Password confirm is required")
        @Min(value = 8, message = "Password must be at least 8 characters")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
        private String confirmPassword;
}
