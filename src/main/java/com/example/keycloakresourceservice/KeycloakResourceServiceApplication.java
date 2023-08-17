package com.example.keycloakresourceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class KeycloakResourceServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KeycloakResourceServiceApplication.class, args);
        System.out.println("__________________________________Branch version is READY TO WORK!!!!!__________________________________");
    }

}
