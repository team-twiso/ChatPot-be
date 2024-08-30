package site.chatpot.domain.user.repository;

import java.util.Optional;
import site.chatpot.domain.user.entity.User;

public interface UserRepositoryCustom {
    Optional<User> getUser(String email);
}
