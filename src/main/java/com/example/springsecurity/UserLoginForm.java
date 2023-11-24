package com.example.springsecurity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginForm {
    private String username;
    private String password;
}
