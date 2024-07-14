package study.openfeign.application.client.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import study.openfeign.dto.kakao.profile.KakaoUserProfile;

@FeignClient(name = "kakaoUserInfoClient", url = "https://kapi.kakao.com")
public interface KakaoUserInfoClient {

    @GetMapping(value = "/v2/user/me", consumes = "application/x-www-form-urlencoded")
    KakaoUserProfile getUserProfile(@RequestHeader("Authorization") String authorization);
}
