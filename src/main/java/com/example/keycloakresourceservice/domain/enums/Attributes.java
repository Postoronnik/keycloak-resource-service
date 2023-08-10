package com.example.keycloakresourceservice.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Attributes {
    DEPARTMENT("department");

    private final String name;

    public static List<Attributes> getPermissions() {
        return List.of(Attributes.values());
    }
}
