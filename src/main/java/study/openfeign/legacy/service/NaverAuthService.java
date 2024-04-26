package study.openfeign.legacy.service;

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
    private final NaverAuthProperties naverAuthProperties;

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
                "client_id", naverAuthProperties.getClientId(),
                "client_secret", naverAuthProperties.getClientSecret(),
                "status", naverAuthProperties.getState());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return defaultService.restTemplate(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request, NaverAuthToken.class)
                .accessToken();
    }

    @Override
    public UserProfile getUserProfile(String accessToken) {
        HttpHeaders headers = defaultService.setHttpHeaders("Authorization", "Bearer " + accessToken);
        HttpEntity<MultiValueMap<String, String>> request =  new HttpEntity<>(headers);

        return defaultService.restTemplate(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                request,
                NaverUserProfile.class);
    }
}
