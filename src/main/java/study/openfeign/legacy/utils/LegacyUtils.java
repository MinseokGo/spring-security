package study.openfeign.legacy.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyUtils {

    private final AuthProperties authProperties;

    public String kakaoURL() {
        return "redirect:https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + authProperties.getClientId()
                + "&redirect_uri=" + authProperties.getRedirectUri();
    }

    public String googleURL() {
        return "redirect:https://accounts.google.com/o/oauth2/v2/auth"
                + "?response_type=code"
                + "&client_id=" + "902539090164-4h10hioh6e01p1eoo1lur7ut9bjlhj6v.apps.googleusercontent.com"
                + "&redirect_uri=" + "http://localhost:8080/login/google/redirect"
                + "&scope=email profile";
    }

    public String naverURL() {
        return "redirect:https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + "nHDSZ2mlOZaoxWrR8Ta5"
                + "&redirect_uri=" + "http://localhost:8080/login/naver/redirect"
                + "&state=minseok";
    }
}
