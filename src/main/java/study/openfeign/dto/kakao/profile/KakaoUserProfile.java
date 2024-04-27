package study.openfeign.dto.kakao.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import study.openfeign.domain.Service;
import study.openfeign.domain.User;
import study.openfeign.dto.UserProfile;

public record KakaoUserProfile(
        Long id,
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount) implements UserProfile {

    @Override
    public User toEntity() {
        return User.builder()
                .name(kakaoAccount.profile().nickname())
                .email(kakaoAccount.email())
                .service(Service.KAKAO)
                .build();
    }
}
