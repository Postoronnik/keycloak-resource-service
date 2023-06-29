package com.example.keycloakresourceservice.service;


import com.example.keycloakresourceservice.domain.Permissions;
import com.example.keycloakresourceservice.domain.UserInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {

    public UserInfo getUserInfo() {
        final var context = SecurityContextHolder.getContext().getAuthentication();
        final var credentials = (Jwt)context.getCredentials();
        final var username = (String) credentials.getClaims().get("preferred_username");
        final var realmAccess = Optional.ofNullable((Map<String, Object>) credentials.getClaims().get("realm_access"));
        final var userRoles = (List<String>) realmAccess.orElseThrow().get("roles");

        return new UserInfo(username, userRoles, getPermissions(credentials.getClaims()));
    }

    public Authentication getAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Map<String, String> getPermissions(Map<String, Object> claims) {
        final var definedPermissions = Permissions.getPermissions();

        return definedPermissions
                .stream()
                .map(permission -> Map.entry(permission.getName(), (String) claims.get(permission.getName())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
