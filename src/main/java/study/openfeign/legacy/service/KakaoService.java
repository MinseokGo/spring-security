package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import study.openfeign.legacy.AuthService;

public class KakaoService implements AuthService {

    @Override
    public void create(String code) {

    }

    @Override
    public void getAccessToken(String code) {
        HttpHeaders headers = setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = getAuthorizationToken(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", kakaoClientId,
                "redirect_uri", kakaoRedirectUri,
                "client_secret", kakaoClientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
    }

    @Override
    public void getUserProfile(String accessToken) {

    }
}
