package com.example.springsecurity.jwt;

import com.example.springsecurity.UserLoginForm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//사용자 인증 처리 필터
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JWTLoginFilter() {
        setFilterProcessesUrl("/login");
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UserLoginForm userLoginForm = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);
        UsernamePasswordAuthenticationFilter token = new UsernamePasswordAuthenticationFilter(
                userLoginForm.getUsername(), userLoginForm.getPassword(), null);
        return getAuthenticationManager().authenticate(token);
    }
}