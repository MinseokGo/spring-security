package study.openfeign.dto.naver;

import study.openfeign.domain.Service;
import study.openfeign.domain.User;
import study.openfeign.dto.UserProfile;

public record NaverUserProfile(Response response) implements UserProfile {

    @Override
    public User toEntity() {
        return User.builder()
                .name(response.name())
                .email(response.email())
                .service(Service.NAVER)
                .build();
    }
}
