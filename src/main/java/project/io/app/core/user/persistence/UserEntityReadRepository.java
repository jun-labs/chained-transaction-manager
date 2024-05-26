package project.io.app.core.user.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import static project.io.app.common.configuration.database.Constant.SOURCE_JPA_QUERY_FACTORY;
import static project.io.app.common.configuration.database.Constant.TARGET_JPA_QUERY_FACTORY;
import static project.io.app.core.user.domain.QUser.user;
import project.io.app.core.user.domain.User;
import project.io.app.core.user.domain.UserReadRepository;

import java.util.Optional;

@Repository
public class UserEntityReadRepository implements UserReadRepository {

    private final JPAQueryFactory sourceQueryFactory;
    private final JPAQueryFactory targetQueryFactory;

    public UserEntityReadRepository(
        @Qualifier(SOURCE_JPA_QUERY_FACTORY)
        JPAQueryFactory sourceQueryFactory,

        @Qualifier(TARGET_JPA_QUERY_FACTORY)
        JPAQueryFactory targetQueryFactory
    ) {
        this.sourceQueryFactory = sourceQueryFactory;
        this.targetQueryFactory = targetQueryFactory;
    }

    @Override
    public Optional<User> findSourceById(Long sourceId) {
        return Optional.ofNullable(
            sourceQueryFactory.selectFrom(user)
                .where(user.id.eq(sourceId))
                .fetchOne()
        );
    }

    @Override
    public Optional<User> findTargetById(Long targetId) {
        return Optional.ofNullable(
            targetQueryFactory.selectFrom(user)
                .where(user.id.eq(targetId))
                .fetchOne()
        );
    }
}
