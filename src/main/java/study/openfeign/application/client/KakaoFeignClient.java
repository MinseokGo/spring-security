package study.openfeign.application;

import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.openfeign.config.OpenFeignConfig;
import study.openfeign.dto.kakao.KakaoAuthToken;

@FeignClient(name = "kakaoFeignClient", configuration = OpenFeignConfig.class)
public interface KakaoFeignClient {

    @PostMapping(consumes = "application/x-www-form-urlencoded")
    KakaoAuthToken getAccessToken(URI baseUrl,
                                  @RequestParam("grant_type") String grantType,
                                  @RequestParam("client_id") String clientId,
                                  @RequestParam("redirect_uri") String redirectUri,
                                  @RequestParam("code") String code);
}

