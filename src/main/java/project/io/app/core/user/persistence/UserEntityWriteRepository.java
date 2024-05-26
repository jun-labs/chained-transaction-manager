package project.io.app.core.user.persistence;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import static project.io.app.common.configuration.database.Constant.SOURCE_ENTITY_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_ENTITY_MANAGER;
import project.io.app.core.user.domain.User;
import project.io.app.core.user.domain.UserWriteRepository;

@Repository
public class UserEntityWriteRepository implements UserWriteRepository {

    private final EntityManager sourceEntityManager;
    private final EntityManager targetEntityManager;

    public UserEntityWriteRepository(
        @Qualifier(SOURCE_ENTITY_MANAGER)
        EntityManager sourceEntityManager,

        @Qualifier(TARGET_ENTITY_MANAGER)
        EntityManager targetEntityManager
    ) {
        this.sourceEntityManager = sourceEntityManager;
        this.targetEntityManager = targetEntityManager;
    }

    @Override
    public User saveSource(User user) {
        sourceEntityManager.persist(user);
        return user;
    }

    @Override
    public User saveTarget(User user) {
        targetEntityManager.persist(user);
        return user;
    }
}
