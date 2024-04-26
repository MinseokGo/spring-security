package study.openfeign.legacy.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.openfeign.legacy.service.LegacyService;
import study.openfeign.legacy.utils.LegacyUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/legacy/login")
public class LegacyController {

    private final LegacyUtils legacyUtils;
    private final LegacyService legacyService;

    @GetMapping("/kakao")
    public String callKakaoLogin() {
        return legacyUtils.kakaoURL();
    }

    @ResponseBody
    @GetMapping("/kakao/redirect")
    public String redirectKakao(@RequestParam("code") String code) {
        legacyService.createKakaoUser(code);
        return "good";
    }

    @GetMapping("/google")
    public String callGoogleLogin() {
        return legacyUtils.googleURL();
    }

    @ResponseBody
    @GetMapping("/google/redirect")
    public String redirectGoogle(@RequestParam("code") String code) {
        legacyService.createGoogleUser(code);
        return "good";
    }

    @GetMapping("/naver")
    public String callNaverLogin() {
        return legacyUtils.naverURL();
    }

    @ResponseBody
    @GetMapping("/naver/redirect")
    public String redirectNaver(@RequestParam("code") String code) {
        legacyService.createNaverUser(code);
        return "good";
    }
}
