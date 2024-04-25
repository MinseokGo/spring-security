package study.openfeign.legacy.dto.naver;

import study.openfeign.legacy.service.UserProfile;

public record NaverUserProfile(Response response) implements UserProfile {
}
