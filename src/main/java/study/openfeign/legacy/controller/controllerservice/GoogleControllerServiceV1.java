package study.openfeign.legacy.controller.controllerservice;

import static study.openfeign.legacy.utils.Constants.GOOGLE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.GoogleAuthProperties;
import study.openfeign.legacy.service.GooglAuthService;
import study.openfeign.legacy.utils.URLUtils;
import study.openfeign.presentation.controllerservice.ControllerService;

@Component("/v1/" + GOOGLE)
@RequiredArgsConstructor
public class GoogleControllerServiceV1 implements ControllerService {

    private final GoogleAuthProperties authProperties;
    private final GooglAuthService authService;

    @Override
    public String getRedirectURL() {
        return URLUtils.createURL(authProperties);
    }

    @Override
    public String authorize(String code) {
        authService.create(code);
        return code;
    }
}
