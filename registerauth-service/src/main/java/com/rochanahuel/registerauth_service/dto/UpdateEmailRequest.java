package com.rochanahuel.registerauth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateEmailRequest(
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email field cannot be empty")
        String email
) {
}
