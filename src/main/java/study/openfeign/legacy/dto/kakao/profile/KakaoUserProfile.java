package study.openfeign.legacy.dto.kakao.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoUserProfile(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) {
}
