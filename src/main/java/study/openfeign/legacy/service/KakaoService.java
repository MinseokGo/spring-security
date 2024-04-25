package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import study.openfeign.legacy.dto.kakao.KakaoAuthToken;
import study.openfeign.legacy.dto.kakao.profile.KakaoUserProfile;

@Service
@RequiredArgsConstructor
public class KakaoService implements AuthService {

    private final DefaultService defaultService;

    @Value("${kakao.clientId}")
    private String clientId;

    @Value("${kakao.redirectUri}")
    private String redirectUri;

    @Value("${kakao.clientSecret}")
    private String clientSecret;

    @Override
    public void create(String code) {
        String accessToken = getAccessToken(code);
        getUserProfile(accessToken);
    }

    @Override
    public String getAccessToken(String code) {
        HttpHeaders headers = defaultService.setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = defaultService.createRequestBody(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", clientId,
                "redirect_uri", redirectUri,
                "client_secret", clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return defaultService.restTemplate(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoAuthToken.class)
                .accessToken();
    }

    @Override
    public KakaoUserProfile getUserProfile(String accessToken) {
        HttpHeaders headers = defaultService.setHttpHeaders(
                "Authorization", "Bearer " + accessToken,
                "Content-type", X_WWW_URL_ENCODED_TYPE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return defaultService.restTemplate(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                KakaoUserProfile.class);
    }
}
