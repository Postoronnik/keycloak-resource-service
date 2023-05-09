package com.example.keycloakresourceservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {


    @GetMapping("/welcome")
    public String status() {
        return "Hello you are authorized";
    }

    @GetMapping("/manager")
    public String managerLogin() {
        return "Welcome manager/director";
    }

    @GetMapping("/director")
    public String directorLogin() {
        return "Welcome director";
    }

}
