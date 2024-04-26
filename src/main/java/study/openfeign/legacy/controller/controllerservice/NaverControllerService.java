package study.openfeign.legacy.controller.controllerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.NaverAuthProperties;
import study.openfeign.legacy.service.NaverAuthService;
import study.openfeign.legacy.utils.URLUtils;

@Component
@RequiredArgsConstructor
public class NaverControllerService implements ControllerService {

    private final NaverAuthProperties authProperties;
    private final NaverAuthService authService;

    @Override
    public String create() {
        return URLUtils.createURL(authProperties);
    }

    @Override
    public void auth(String code) {
        authService.create(code);
    }
}
