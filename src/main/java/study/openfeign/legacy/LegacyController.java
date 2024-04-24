package study.openfeign.legacy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LegacyController {

    private final LegacyService legacyService;

    @GetMapping("/kakao")
    public String call() {
        return legacyService.uriCreate();
    }

    @ResponseBody
    @GetMapping("/kakao/redirect")
    public String redirect(@RequestParam String code) {
        legacyService.create(code);
        return "good";
    }
}
