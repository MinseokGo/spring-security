package study.openfeign.legacy.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyUtils {

    private final AuthProperties authProperties;

    public String uriCreate() {
        return "redirect:https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + authProperties.getClientId()
                + "&redirect_uri=" + authProperties.getRedirectUri();
    }
}
