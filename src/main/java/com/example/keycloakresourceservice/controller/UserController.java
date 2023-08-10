package com.example.keycloakresourceservice.controller;

import com.example.keycloakresourceservice.domain.UserInfo;
import com.example.keycloakresourceservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/welcome")
    public String status() {
        return "Hello you are authorized";
    }

    @GetMapping("/manager")
    public String managerLogin() {
        return "Welcome manager/director";
    }

    @GetMapping("/userDetails")
    public UserInfo getUserDetails() {
        return userService.getUserInfo();
    }

    @GetMapping("/authenticationContext")
    public Authentication getAuthenticationContext() {
        return userService.getAuthenticationContext();
    }
}
