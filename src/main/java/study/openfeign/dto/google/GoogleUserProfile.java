package study.openfeign.dto.google;

import study.openfeign.domain.Service;
import study.openfeign.domain.User;
import study.openfeign.dto.UserProfile;

public record GoogleUserProfile(String name, String email) implements UserProfile {

    @Override
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .service(Service.GOOGLE)
                .build();
    }
}
