package study.openfeign.application.client.google;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import study.openfeign.dto.google.GoogleUserProfile;

@FeignClient(name = "googleUserInfoClient", url = "https://www.googleapis.com")
public interface GoogleUserInfoClient {

    @GetMapping("/userinfo/v2/me")
    GoogleUserProfile getUserProfile(@RequestHeader("Content-Type") String contentType,
                                     @RequestHeader("Authorization") String authorization);
}
