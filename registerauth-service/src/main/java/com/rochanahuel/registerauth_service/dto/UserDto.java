package com.rochanahuel.registerauth_service.dto;

import com.rochanahuel.registerauth_service.utils.Role;
public record UserDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        Role role
) {
}
