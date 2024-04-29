package study.openfeign.presentation.controllerservice;

import static study.openfeign.legacy.utils.Constants.KAKAO;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.openfeign.application.KakaoOpenFeignAuthService;
import study.openfeign.legacy.properties.KakaoAuthProperties;
import study.openfeign.legacy.utils.URLUtils;

@Component(KAKAO)
@RequiredArgsConstructor
public class KakaoControllerServiceV2 implements ControllerService {

    private final KakaoAuthProperties authProperties;
    private final KakaoOpenFeignAuthService openFeignAuthService;

    @Override
    public String getRedirectURL() {
        return URLUtils.createURL(authProperties);
    }

    @Override
    public String authorize(String code) {
        return openFeignAuthService.authorization(code);
    }
}
