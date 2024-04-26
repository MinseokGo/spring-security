package study.openfeign.legacy.controller.controllerservice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.GoogleAuthProperties;
import study.openfeign.legacy.service.GooglAuthService;
import study.openfeign.legacy.utils.URLUtils;

@Component
@RequiredArgsConstructor
public class GoogleControllerService implements ControllerService {

    private final GoogleAuthProperties authProperties;
    private final GooglAuthService authService;

    @Override
    public String create() {
        return URLUtils.createURL(authProperties);
    }

    @Override
    public void auth(String code) {
        authService.create(code);
    }
}
