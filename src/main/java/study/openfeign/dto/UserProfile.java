package study.openfeign.dto;

import study.openfeign.domain.User;

public interface UserProfile {

    User toEntity();
}
