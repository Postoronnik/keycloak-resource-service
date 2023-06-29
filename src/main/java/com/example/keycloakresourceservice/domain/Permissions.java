package com.example.keycloakresourceservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Permissions {
    CUSTOM_PERMISSION("customPermission");

    private final String name;

    public static List<Permissions> getPermissions() {
        return List.of(Permissions.values());
    }
}
