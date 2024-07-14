package study.openfeign.application;

import static study.openfeign.legacy.utils.Constants.AUTHORIZATION_CODE;
import static study.openfeign.legacy.utils.Constants.TOKEN_PREFIX;
import static study.openfeign.legacy.utils.Constants.X_WWW_URL_ENCODED_TYPE;

import jakarta.persistence.EntityExistsException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.openfeign.application.client.google.GoogleTokenClient;
import study.openfeign.application.client.google.GoogleUserInfoClient;
import study.openfeign.domain.User;
import study.openfeign.dto.google.GoogleUserProfile;
import study.openfeign.legacy.properties.GoogleAuthProperties;
import study.openfeign.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleOpenFeignAuthService implements OpenFeignAuthService {

    private final UserRepository userRepository;

    private final GoogleAuthProperties googleAuthProperties;

    private final GoogleTokenClient googleTokenClient;
    private final GoogleUserInfoClient googleUserInfoClient;

    @Override
    @Transactional
    public String authorization(String code) {
        String token = getAccessToken(code);
        GoogleUserProfile profile = getUserProfile(token);
        User user = profile.toEntity();
        checkedDuplicateEmail(user);
        userRepository.save(user);
        return user.toString();
    }

    private String getAccessToken(String code) {
        return googleTokenClient.getAuthToken(
                        AUTHORIZATION_CODE,
                        code,
                        googleAuthProperties.getClientId(),
                        googleAuthProperties.getRedirectUri(),
                        googleAuthProperties.getClientSecret())
                        .accessToken();
    }

    private GoogleUserProfile getUserProfile(String token) {
        return googleUserInfoClient.getUserProfile(
                X_WWW_URL_ENCODED_TYPE,
                TOKEN_PREFIX + token);
    }

    private void checkedDuplicateEmail(User other) {
        Optional<User> optionalUser = userRepository.findByEmail(other.getEmail());
        if (optionalUser.isPresent()) {
            throw new EntityExistsException("duplicate email");
        }
    }
}
