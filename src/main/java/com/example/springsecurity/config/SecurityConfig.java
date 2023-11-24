package com.example.springsecurity.config;

import com.example.springsecurity.jwt.JWTCheckFilter;
import com.example.springsecurity.jwt.JWTLoginFilter;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JWTLoginFilter jwtLoginFilter = new JWTLoginFilter();
        JWTCheckFilter jwtCheckFilter = new JWTCheckFilter(userService);

        http
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtCheckFilter, OncePerRequestFilter.class);
        return http.build();
    }
}
