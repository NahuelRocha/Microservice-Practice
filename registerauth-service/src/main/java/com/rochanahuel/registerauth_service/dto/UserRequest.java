package com.rochanahuel.registerauth_service.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserRequest(
        @NotBlank(message = "The name field cannot be empty")
        String firstname,
        @NotBlank(message = "Last name field cannot be empty")
        String lastname,
        @NotNull(message = "Date of birth cannot be empty")
        LocalDate dateOfBirth,
        @Email(message = "Invalid email format")
        @NotBlank(message = "Email field cannot be empty")
        String email,
        @NotBlank(message = "Phone field cannot be empty")
        String phone,
        @NotBlank(message = "Address field cannot be empty")
        String address,
        @NotBlank(message = "Password field cannot be empty")
        @Size(min = 5, max = 20, message = "Password must be between 5 and 20 characters")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$", message = "Password must contain at least one letter and one number")
        String password
){}
