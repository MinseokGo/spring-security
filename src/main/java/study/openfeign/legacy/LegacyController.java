package study.openfeign.legacy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
        return legacyUtils.uriCreate();
    }

    @ResponseBody
    @GetMapping("/kakao/redirect")
    public String redirectKakao(@RequestParam("code") String code) {
        legacyService.create(code);
        return "good";
    }

    @GetMapping("/google")
    public String callGoogleLogin() {
        return legacyUtils.googleUri();
    }

    @GetMapping("/google/redirect")
    public void redirectGoogle(@RequestParam("code") String code) {
        System.out.println(code);
    }
}
