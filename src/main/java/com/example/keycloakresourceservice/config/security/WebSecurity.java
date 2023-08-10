package com.example.keycloakresourceservice.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/users/welcome")
                .permitAll()
                .requestMatchers("/users/manager", "/users/userDetails", "/users/authenticationContext", "/employees/search")
                .hasAnyRole("manager")
        ).oauth2ResourceServer(oauth2 -> oauth2
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
        );

        return http.build();
    }

}