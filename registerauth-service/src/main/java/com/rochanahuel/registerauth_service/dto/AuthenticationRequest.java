package com.rochanahuel.registerauth_service.dto;

public record AuthenticationRequest(
        String email,
        String password
) {
}
