package com.example.keycloakresourceservice.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

public class SecurityContextHelper {

    public static String getDepartment() {
        final var jwtAuthenticationContext = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)jwtAuthenticationContext.getCredentials();

        return (String) credentials.getClaims().get("department");
    }

    public static List<String> getDepartments() {
        final var jwtAuthenticationContext = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)jwtAuthenticationContext.getCredentials();
        final var groups = (List<String>) credentials.getClaims().get("departments");

        return groups
                .stream()
                .map(group ->
                        group
                        .replace("Department", "")
                        .trim()
                )
                .toList();
    }
}
