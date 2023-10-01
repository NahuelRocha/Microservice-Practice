package com.rochanahuel.registerauth_service.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {


    USER_ACCESS("user:access"),
    ADMIN_ACCESS("admin:access")

    ;

    @Getter
    private final String permission;
}
