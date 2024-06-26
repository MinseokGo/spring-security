package study.openfeign.legacy.service;

import study.openfeign.dto.UserProfile;

public interface AuthService {

    void create(String code);

    String getAccessToken(String code);

    UserProfile getUserProfile(String accessToken);
}
