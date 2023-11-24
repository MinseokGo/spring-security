package com.example.springsecurity.service;

import com.example.springsecurity.domain.Authority;
import com.example.springsecurity.domain.User;
import com.example.springsecurity.repository.UserRepository;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) throws DataIntegrityViolationException {
        return userRepository.save(user);
    }

    public void addAuthority(Long userId, String authority) {
        userRepository.findById(userId)
                .ifPresent(
                        user -> {
                            Authority newRole = new Authority(user.getId(), authority);
                            if (user.getAuthorities() == null) {
                                HashSet<Authority> authorities = new HashSet<>();
                                authorities.add(newRole);
                                user.setAuthorities(authorities);
                                save(user);
                            } else if (!user.getAuthorities().contains(newRole)) {
                                HashSet<Authority> authorities = new HashSet<>();
                                authorities.addAll(user.getAuthorities());
                                authorities.add(newRole);
                                user.setAuthorities(authorities);
                                save(user);
                            }
                        });
    }
}