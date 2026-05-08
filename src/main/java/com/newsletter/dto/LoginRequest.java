package com.newsletter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        @Email(message = "Invalid email format")
        String email,

        @NotBlank
        String password
) {
}
