package study.openfeign.legacy.controller;

import static study.openfeign.legacy.utils.Constants.GOOGLE;
import static study.openfeign.legacy.utils.Constants.KAKAO;
import static study.openfeign.legacy.utils.Constants.NAVER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.properties.GoogleAuthProperties;
import study.openfeign.legacy.properties.KakaoAuthProperties;
import study.openfeign.legacy.properties.NaverAuthProperties;
import study.openfeign.legacy.service.GooglAuthService;
import study.openfeign.legacy.service.KakaoAuthService;
import study.openfeign.legacy.service.NaverAuthService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ControllerServiceCondition {

    private final KakaoAuthProperties kakaoAuthProperties;
    private final GoogleAuthProperties googleAuthProperties;
    private final NaverAuthProperties naverAuthProperties;

    private final KakaoAuthService kakaoAuthService;
    private final GooglAuthService googlAuthService;
    private final NaverAuthService naverAuthService;

    public ControllerService getControllerService(String name) {
        ControllerService controllerService = null;
        if (name.equals(KAKAO)) {
            controllerService = new ControllerService(kakaoAuthProperties, kakaoAuthService);
        }
        if (name.equals(GOOGLE)) {
            controllerService = new ControllerService(googleAuthProperties, googlAuthService);
        }
        if (name.equals(NAVER)) {
            controllerService = new ControllerService(naverAuthProperties, naverAuthService);
        }
        log.info("controllerService={}", controllerService);
        return controllerService;
    }
}
