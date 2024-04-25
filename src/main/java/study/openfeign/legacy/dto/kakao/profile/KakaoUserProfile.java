package study.openfeign.legacy.dto.kakao.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import study.openfeign.legacy.service.UserProfile;

public record KakaoUserProfile(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount) implements UserProfile {
}
