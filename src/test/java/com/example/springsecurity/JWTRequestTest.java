package com.example.springsecurity;

import com.example.springsecurity.domain.User;
import com.example.springsecurity.repository.UserRepository;
import com.example.springsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
class JWTRequestTest extends WebIntegrationTest {
    private final UserService userService;
    private final UserRepository userRepository;

    @BeforeEach
    void before() {
        userRepository.deleteAll();
        User user = userService.save(User.builder()
                .email("user")
                .password("1234")
                .enabled(true)
                .build());
        userService.addAuthority(user.getId(), "ROLE_USER");
    }

    @DisplayName("Hello 메시지를 받아온다.")
    @Test
    void test1() {
        RestTemplate client = new RestTemplate();
        HttpEntity<UserLoginForm> body = new HttpEntity<>(
                UserLoginForm.builder()
                        .username("user")
                        .password("1234")
                        .build());

        ResponseEntity<User> response = client.exchange(uri("/login"),
                HttpMethod.POST,
                body,
                User.class);
    }
}
