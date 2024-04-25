package study.openfeign.legacy.service;

import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import study.openfeign.legacy.dto.UserProfile;
import study.openfeign.legacy.dto.google.GoogleAuthToken;
import study.openfeign.legacy.dto.google.GoogleUserProfile;

@Service
@RequiredArgsConstructor
public class GooglAuthService implements AuthService {

    private final DefaultService defaultService;

    @Value("${google.clientId}")
    private String googleClientId;

    @Value("${google.redirectUri}")
    private String googleRedirectUri;

    @Value("${google.clientSecret}")
    private String googleClientSecret;

    @Override
    public void create(String code) {
        String accessToken = getAccessToken(code);
        getUserProfile(accessToken);
    }

    @Override
    public String getAccessToken(String code) {
        HttpHeaders headers = defaultService.setHttpHeaders("Content-type", X_WWW_URL_ENCODED_TYPE);
        MultiValueMap<String, String > body = defaultService.createRequestBody(
                "grant_type", "authorization_code",
                "code", code,
                "client_id", googleClientId,
                "redirect_uri", googleRedirectUri,
                "client_secret", googleClientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        return defaultService.restTemplate(
                "https://oauth2.googleapis.com/token",
                HttpMethod.POST,
                request,
                GoogleAuthToken.class)
                .accessToken();
    }

    @Override
    public UserProfile getUserProfile(String accessToken) {
        HttpHeaders headers = defaultService.setHttpHeaders(
                "Authorization", "Bearer " + accessToken,
                "Content-type", X_WWW_URL_ENCODED_TYPE);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        return defaultService.restTemplate(
                "https://www.googleapis.com/oauth2/v1/userinfo",
                HttpMethod.GET,
                request,
                GoogleUserProfile.class);
    }
}
