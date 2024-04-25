package study.openfeign.legacy.dto.naver;

import study.openfeign.legacy.dto.UserProfile;

public record NaverUserProfile(Response response) implements UserProfile {
}
