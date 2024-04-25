package study.openfeign.legacy.dto.google;

import study.openfeign.legacy.dto.UserProfile;

public record GoogleUserProfile(String name, String email) implements UserProfile {
}
