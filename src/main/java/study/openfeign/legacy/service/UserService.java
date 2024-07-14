package study.openfeign.legacy.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.openfeign.domain.User;
import study.openfeign.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User save(User user) {
        Optional<User> findUser = userRepository.findByEmail(user.getEmail());
        return findUser.orElseGet(() -> userRepository.save(user));
    }
}
