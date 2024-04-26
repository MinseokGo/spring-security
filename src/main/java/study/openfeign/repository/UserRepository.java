package study.openfeign.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.openfeign.domain.User;

public interface UserRepository extends JpaRepository<Long, User> {
}
