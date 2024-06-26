package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

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
import study.openfeign.dto.google.GoogleAuthToken;
import study.openfeign.dto.google.GoogleUserProfile;
import study.openfeign.dto.kakao.KakaoAuthToken;
import study.openfeign.dto.kakao.profile.KakaoUserProfile;
import study.openfeign.dto.naver.NaverAuthToken;
import study.openfeign.dto.naver.NaverUserProfile;

@Slf4j
@Service
@RequiredArgsConstructor
public class LegacyService {

    private final RestTemplate restTemplate;

    @Value("${kakao.clientId}")
    private String kakaoClientId;

    @Value("${kakao.redirectUri}")
    private String kakaoRedirectUri;

    @Value("${kakao.clientSecret}")
    private String kakaoClientSecret;

    @Value("${google.clientId}")
    private String googleClientId;

    @Value("${google.redirectUri}")
    private String googleRedirectUri;

    @Value("${google.clientSecret}")
    private String googleClientSecret;

    @Value("${naver.clientId}")
    private String naverClientId;

    @Value("${naver.clientSecret}")
    private String naverClientSecret;

    public void createKakaoUser(String code) {
        KakaoAuthToken authToken = getKakaoAccessToken(code);
        KakaoUserProfile userProfile = getKakaoUserProfile(authToken);
    }

    private KakaoAuthToken getKakaoAccessToken(String code) {
        HttpHeaders headers = setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = getAuthorizationToken(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", kakaoClientId,
                "redirect_uri", kakaoRedirectUri,
                "client_secret", kakaoClientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                KakaoAuthToken.class);
    }

    private KakaoUserProfile getKakaoUserProfile(KakaoAuthToken authToken) {
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

    public void createGoogleUser(String code) {
        GoogleAuthToken authToken = getGoogleAuthToken(code);
        GoogleUserProfile userProfile = getGoogleUserProfile(authToken);
    }

    private GoogleAuthToken getGoogleAuthToken(String code) {
        HttpHeaders headers = setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String > body = getAuthorizationToken(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", googleClientId,
                "redirect_uri", googleRedirectUri,
                "client_secret", googleClientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                GoogleAuthToken.class
        );
    }

    private GoogleUserProfile getGoogleUserProfile(GoogleAuthToken googleAuthToken) {
        HttpHeaders headers = setHttpHeaders(
                "Authorization", "Bearer " + googleAuthToken.accessToken(),
                "Content-type", X_WWW_URL_ENCODED_TYPE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return restTemplate(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                request,
                GoogleUserProfile.class);
    }

    public void createNaverUser(String code) {
        NaverAuthToken authToken = getNaverAuthToken(code);
        NaverUserProfile userProfile = getnNaverUserProfile(authToken);
    }

    private NaverAuthToken getNaverAuthToken(String code) {
        HttpHeaders headers = setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = getAuthorizationToken(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", naverClientId,
                "client_secret", naverClientSecret,
                "status", "minseok");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return restTemplate(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                NaverAuthToken.class);
    }

    private NaverUserProfile getnNaverUserProfile(NaverAuthToken authToken) {
        HttpHeaders headers = setHttpHeaders("Authorization", "Bearer " + authToken.accessToken());
        HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<>(headers);

        return restTemplate(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                NaverUserProfile.class);
    }

    private HttpHeaders setHttpHeaders(String... values) {
        HttpHeaders headers = new HttpHeaders();
        for (int i = 0; i < values.length; i += 2) {
            headers.add(values[i], values[i + 1]);
        }
        return headers;
    }

    private MultiValueMap<String, String> getAuthorizationToken(String... values) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        for (int i = 0; i < values.length; i += 2) {
            body.add(values[i], values[i + 1]);
        }
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
