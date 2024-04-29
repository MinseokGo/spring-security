package study.openfeign.legacy.controller.controllerservice;

import static study.openfeign.legacy.utils.Constants.KAKAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.KakaoAuthProperties;
import study.openfeign.legacy.service.KakaoAuthService;
import study.openfeign.legacy.utils.URLUtils;
import study.openfeign.presentation.controllerservice.ControllerService;

@Component("/v1/" + KAKAO)
@RequiredArgsConstructor
public class KakaoControllerServiceV1 implements ControllerService {

    private final KakaoAuthProperties authProperties;
    private final KakaoAuthService authService;

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
