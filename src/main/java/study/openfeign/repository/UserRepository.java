package study.openfeign.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import study.openfeign.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
