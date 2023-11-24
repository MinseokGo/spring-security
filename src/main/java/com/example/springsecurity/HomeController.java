package com.example.springsecurity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/greeting")
    public String greeting() {
        return "hello";
    }
}
