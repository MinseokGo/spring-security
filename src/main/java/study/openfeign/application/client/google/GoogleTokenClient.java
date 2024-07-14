package study.openfeign.application.client.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.openfeign.dto.google.GoogleAuthToken;

@FeignClient(name = "googleTokenClient", url = "https://oauth2.googleapis.com")
public interface GoogleTokenClient {

    @PostMapping("/token")
    GoogleAuthToken getAuthToken(@RequestParam("grant_type") String grantType,
                                 @RequestParam("code") String code,
                                 @RequestParam("client_id") String clientId,
                                 @RequestParam("redirect_uri") String requestUri,
                                 @RequestParam("client_secret") String clientSecret);
}
