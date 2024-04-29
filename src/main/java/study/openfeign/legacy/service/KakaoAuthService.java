package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.AUTHORIZATION;
import static study.openfeign.legacy.utils.Constants.AUTHORIZATION_CODE;
import static study.openfeign.legacy.utils.Constants.CLIENT_ID;
import static study.openfeign.legacy.utils.Constants.CODE;
import static study.openfeign.legacy.utils.Constants.CONTENT_TYPE;
import static study.openfeign.legacy.utils.Constants.GRANT_TYPE;
import static study.openfeign.legacy.utils.Constants.REDIRECT_URI;
import static study.openfeign.legacy.utils.Constants.TOKEN_PREFIX;
import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import study.openfeign.domain.User;
import study.openfeign.dto.UserProfile;
import study.openfeign.dto.kakao.KakaoAuthToken;
import study.openfeign.dto.kakao.profile.KakaoUserProfile;
import study.openfeign.legacy.properties.KakaoAuthProperties;

@Service
@RequiredArgsConstructor
public class KakaoAuthService implements AuthService {

    private final UserService userService;
    private final DefaultService defaultService;
    private final KakaoAuthProperties authProperties;

    @Override
    public void create(String code) {
        String accessToken = getAccessToken(code);
        UserProfile userProfile = getUserProfile(accessToken);
        User user = userProfile.toEntity();
        userService.save(user);
    }

    @Override
    public String getAccessToken(String code) {
        HttpHeaders headers = defaultService.setHttpHeaders(CONTENT_TYPE, X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = defaultService.createRequestBody(
                GRANT_TYPE, AUTHORIZATION_CODE,
                CODE, code,
                CLIENT_ID, authProperties.getClientId(),
                REDIRECT_URI, authProperties.getRedirectUri());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return defaultService.restTemplate(
                authProperties.getTokenUri(),
                HttpMethod.POST,
                request,
                KakaoAuthToken.class)
                .accessToken();
    }

    @Override
    public KakaoUserProfile getUserProfile(String accessToken) {
        HttpHeaders headers = defaultService.setHttpHeaders(
                AUTHORIZATION, TOKEN_PREFIX + accessToken,
                CONTENT_TYPE, X_WWW_URL_ENCODED_TYPE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return defaultService.restTemplate(
                authProperties.getUserInfoUri(),
                HttpMethod.POST,
                request,
                KakaoUserProfile.class);
    }
}
