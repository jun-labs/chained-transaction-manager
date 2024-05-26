package project.io.app.core.user.domain;

import java.util.*;

public interface UserReadRepository {
    Optional<User> findSourceById(Long userId);

    Optional<User> findTargetById(Long targetId);
}
