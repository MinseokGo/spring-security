package study.openfeign.legacy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import study.openfeign.legacy.dto.KakaoAuthToken;
import study.openfeign.legacy.dto.profile.KakaoUserProfile;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegacyService {

    public static final String X_WWW_URL_ENCODED_TYPE = "application/x-www-form-urlencoded;charset=utf-8";

    private final RestTemplate restTemplate;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    public String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    public String redirectUri;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    public String clientSecret;

    public String uriCreate() {
        return "redirect:https://kauth.kakao.com/oauth/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri;
    }

    public void create(String code) {
        KakaoAuthToken authToken = getAccessToken(code);
        KakaoUserProfile userProfile = getUserProfile(authToken);
    }

    private KakaoAuthToken getAccessToken(String code) {
        HttpHeaders headers = setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = getAuthorizationToken(code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoAuthToken.class);
    }

    private KakaoUserProfile getUserProfile(KakaoAuthToken authToken) {
        HttpHeaders headers = setHttpHeaders(
                "Authorization", "Bearer " + authToken.accessToken(),
                "Content-type", X_WWW_URL_ENCODED_TYPE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                KakaoUserProfile.class);
    }

    private HttpHeaders setHttpHeaders(String... values) {
        HttpHeaders headers = new HttpHeaders();
        for (int i = 0; i < values.length; i += 2) {
            headers.add(values[i], values[i + 1]);
        }
        return headers;
    }

    private MultiValueMap<String, String> getAuthorizationToken(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);
        return body;
    }

    private <T> T restTemplate(String requestURL,
                               HttpMethod httpMethod,
                               HttpEntity<MultiValueMap<String, String>> request,
                               Class<T> mappingClass) {

        return restTemplate.exchange(requestURL, httpMethod, request, mappingClass)
                .getBody();
    }
}
