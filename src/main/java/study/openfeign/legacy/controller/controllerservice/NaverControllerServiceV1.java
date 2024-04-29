package study.openfeign.legacy.controller.controllerservice;

import static study.openfeign.legacy.utils.Constants.NAVER;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.NaverAuthProperties;
import study.openfeign.legacy.service.NaverAuthService;
import study.openfeign.legacy.utils.URLUtils;
import study.openfeign.presentation.controllerservice.ControllerService;

@Component("/v1/" + NAVER)
@RequiredArgsConstructor
public class NaverControllerServiceV1 implements ControllerService {

    private final NaverAuthProperties authProperties;
    private final NaverAuthService authService;

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
