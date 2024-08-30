package site.chatpot.domain.user.repository;


import static site.chatpot.domain.image.entity.QImage.image;
import static site.chatpot.domain.user.entity.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import site.chatpot.domain.user.entity.User;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Optional<User> getUser(String email) {
        return Optional.ofNullable(queryFactory
                .select(user)
                .from(user)
                .where(user.email.eq(email))
                .leftJoin(user.image, image).fetchJoin()
                .fetchOne());
    }
}
