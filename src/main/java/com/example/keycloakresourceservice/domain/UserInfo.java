package com.example.keycloakresourceservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserInfo {
    private String username;
    private List<String> userRoles;
    private String attributes;
}
