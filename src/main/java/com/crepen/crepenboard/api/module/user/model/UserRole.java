package com.crepen.crepenboard.api.module.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserRole {
    ROLE_COMMON_USER("ROLE_COMMON_USER"),
    ROLE_SUDO("ROLE_SUDO"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DEMO("ROLE_DEMO"),
    ROLE_BOARD_ADMIN("ROLE_BOARD_ADMIN"),
    ROLE_BOARD_MANAGER("ROLE_BOARD_MANAGER")
    ;


    public final String key;
}
