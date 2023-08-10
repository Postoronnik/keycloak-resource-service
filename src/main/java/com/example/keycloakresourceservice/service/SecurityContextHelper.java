package com.example.keycloakresourceservice.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class SecurityContextHelper {

    public static String getDepartment() {
        final var context = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)context.getCredentials();

        return (String) credentials.getClaims().get("department");
    }
}
