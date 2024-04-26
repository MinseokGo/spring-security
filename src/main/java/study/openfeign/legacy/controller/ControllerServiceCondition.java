package study.openfeign.legacy.controller;

import static study.openfeign.legacy.utils.Constants.GOOGLE;
import static study.openfeign.legacy.utils.Constants.KAKAO;
import static study.openfeign.legacy.utils.Constants.NAVER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import study.openfeign.legacy.controller.controllerservice.ControllerService;
import study.openfeign.legacy.controller.controllerservice.GoogleControllerService;
import study.openfeign.legacy.controller.controllerservice.KakaoControllerService;
import study.openfeign.legacy.controller.controllerservice.NaverControllerService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ControllerServiceCondition {

    private final KakaoControllerService kakaoControllerService;
    private final GoogleControllerService googleControllerService;
    private final NaverControllerService naverControllerService;

    public ControllerService getControllerService(String name) {
        if (name.equals(KAKAO)) {
            return kakaoControllerService;
        }
        if (name.equals(GOOGLE)) {
            return googleControllerService;
        }
        if (name.equals(NAVER)) {
            return naverControllerService;
        }
        throw new IllegalStateException("Bad Request");
    }
}
