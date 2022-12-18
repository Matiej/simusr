package com.ematiej.simusr.user.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String userRole;

    UserRole(String userRole) {
        this.userRole = userRole;
    }
    /*
    Method with that annotation helps to document role as enum in swagger.
    Without that in swagger appears string. Result - if you use string
    "ROLE_USER" instead of enum , will get 400 http response.
     */
    @JsonCreator
    public static UserRole getUseRoleFromCode(String value) {
        return Arrays.stream(UserRole.values())
                .filter(p -> p.userRole.equals(value))
                .findFirst()
                .orElse(null);
    }

    public String getUserRole() {
        return userRole;
    }
}
