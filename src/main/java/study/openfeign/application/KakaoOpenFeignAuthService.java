package study.openfeign.application;

import static study.openfeign.legacy.utils.Constants.AUTHORIZATION_CODE;
import static study.openfeign.legacy.utils.Constants.TOKEN_PREFIX;

import jakarta.persistence.EntityExistsException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.openfeign.application.client.kakao.KakaoTokenClient;
import study.openfeign.application.client.kakao.KakaoUserInfoClient;
import study.openfeign.domain.User;
import study.openfeign.dto.kakao.profile.KakaoUserProfile;
import study.openfeign.legacy.properties.KakaoAuthProperties;
import study.openfeign.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoOpenFeignAuthService implements OpenFeignAuthService {

    private final UserRepository userRepository;

    private final KakaoAuthProperties authProperties;

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoUserInfoClient kakaoUserInfoClient;

    @Override
    @Transactional
    public String authorization(String code) {
        String token = getAccessToken(code);
        KakaoUserProfile profile = kakaoUserInfoClient.getUserProfile(TOKEN_PREFIX + token);
        User user = profile.toEntity();
        checkedDuplicateEmail(user);
        userRepository.save(user);
        return user.toString();
    }

    private String getAccessToken(String code) {
        return kakaoTokenClient.getAuthToken(
                        AUTHORIZATION_CODE,
                        authProperties.getClientId(),
                        authProperties.getRedirectUri(),
                        code)
                        .accessToken();
    }

    private void checkedDuplicateEmail(User other) {
        Optional<User> optionalUser = userRepository.findByEmail(other.getEmail());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("duplicate email");
        }
    }
}
