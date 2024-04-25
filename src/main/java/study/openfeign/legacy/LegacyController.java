package study.openfeign.legacy;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import study.openfeign.legacy.utils.AuthProperties;
import study.openfeign.legacy.utils.LegacyUtils;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
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

    @GetMapping("/google/redirect")
    public void redirectGoogle(@RequestParam("code") String code) {
        legacyService.createGoogleUser(code);
    }

    @GetMapping("/naver")
    public String callNaverLogin() {
        return legacyUtils.naverURL();
    }

    @GetMapping("/naver/redirect")
    public void redirectNaver(@RequestParam("code") String code) {
        legacyService.createNaverUser(code);
    }
}
