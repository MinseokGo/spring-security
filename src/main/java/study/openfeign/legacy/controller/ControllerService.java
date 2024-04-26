package study.openfeign.legacy.controller;

import lombok.RequiredArgsConstructor;
import study.openfeign.legacy.service.AuthService;
import study.openfeign.legacy.properties.AuthProperties;
import study.openfeign.legacy.utils.URLUtils;

@RequiredArgsConstructor
public class ControllerService {

    private final AuthProperties authProperties;
    private final AuthService authService;

    public String create() {
        return URLUtils.createURL(authProperties);
    }

    public void auth(String code) {
        authService.create(code);
    }
}
