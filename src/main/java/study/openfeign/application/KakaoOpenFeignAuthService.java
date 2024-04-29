package study.openfeign.application;

import static study.openfeign.legacy.utils.Constants.GRANT_TYPE;

import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.openfeign.application.client.KakaoFeignClient;
import study.openfeign.legacy.properties.KakaoAuthProperties;

@Service
@RequiredArgsConstructor
public class KakaoOpenFeignAuthService implements OpenFeignAuthService {

    public final KakaoFeignClient kakaoFeignClient;
    public final KakaoAuthProperties authProperties;

    @Override
    public String authorization(String code) {
        try {
            URI requestURI = new URI(authProperties.getTokenUri());
            return getAccessToken(code, requestURI);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Bad Request");
        }
    }

    private String getAccessToken(String code, URI requestURI) {
        return kakaoFeignClient.getAuthToken(
                        requestURI,
                        GRANT_TYPE,
                        authProperties.getClientId(),
                        authProperties.getRedirectUri(),
                        code)
                        .accessToken();
    }
}
