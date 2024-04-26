package study.openfeign.legacy.dto;

import study.openfeign.domain.User;

public interface UserProfile {

    User toEntity();
}
