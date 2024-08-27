package site.chatpot.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.chatpot.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
