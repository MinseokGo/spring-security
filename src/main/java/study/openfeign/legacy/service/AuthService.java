package study.openfeign.legacy.service;

import study.openfeign.legacy.dto.kakao.KakaoAuthToken;
import study.openfeign.legacy.dto.kakao.profile.KakaoUserProfile;

public interface AuthService {

    void create(String code);

    String getAccessToken(String code);

    UserProfile getUserProfile(String accessToken);
}
