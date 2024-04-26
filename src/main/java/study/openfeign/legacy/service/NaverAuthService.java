package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.AUTHORIZATION;
import static study.openfeign.legacy.utils.Constants.AUTHORIZATION_CODE;
import static study.openfeign.legacy.utils.Constants.CLIENT_ID;
import static study.openfeign.legacy.utils.Constants.CLIENT_SECRET;
import static study.openfeign.legacy.utils.Constants.CODE;
import static study.openfeign.legacy.utils.Constants.CONTENT_TYPE;
import static study.openfeign.legacy.utils.Constants.GRANT_TYPE;
import static study.openfeign.legacy.utils.Constants.STATUS;
import static study.openfeign.legacy.utils.Constants.TOKEN_PREFIX;
import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import study.openfeign.legacy.dto.UserProfile;
import study.openfeign.legacy.dto.naver.NaverAuthToken;
import study.openfeign.legacy.dto.naver.NaverUserProfile;
import study.openfeign.legacy.properties.NaverAuthProperties;

@Service
@RequiredArgsConstructor
public class NaverAuthService implements AuthService {

    private final DefaultService defaultService;
    private final NaverAuthProperties authProperties;

    @Override
    public void create(String code) {
        String accessToken = getAccessToken(code);
        getUserProfile(accessToken);
    }

    @Override
    public String getAccessToken(String code) {
        HttpHeaders headers = defaultService.setHttpHeaders(CONTENT_TYPE, X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String> body = defaultService.createRequestBody(
                GRANT_TYPE, AUTHORIZATION_CODE,
                CODE, code,
                CLIENT_ID, authProperties.getClientId(),
                CLIENT_SECRET, authProperties.getClientSecret(),
                STATUS, authProperties.getState());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return defaultService.restTemplate(
                authProperties.getTokenUri(),
                HttpMethod.POST,
                request, NaverAuthToken.class)
                .accessToken();
    }

    @Override
    public UserProfile getUserProfile(String accessToken) {
        HttpHeaders headers = defaultService.setHttpHeaders(AUTHORIZATION, TOKEN_PREFIX + accessToken);
        HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<>(headers);

        return defaultService.restTemplate(
                authProperties.getUserInfoUri(),
                HttpMethod.GET,
                request,
                NaverUserProfile.class);
    }
}
