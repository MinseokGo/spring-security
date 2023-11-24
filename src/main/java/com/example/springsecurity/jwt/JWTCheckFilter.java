package com.example.springsecurity.jwt;

import com.example.springsecurity.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.filter.OncePerRequestFilter;

//토큰 검사 후 SecurityContextHolder에 유저 인증 정보 등록
public class JWTCheckFilter extends OncePerRequestFilter {
    private final UserService userService;

    public JWTCheckFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    }
}
