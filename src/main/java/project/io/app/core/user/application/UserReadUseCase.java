package project.io.app.core.user.application;

import project.io.app.core.user.domain.*;

public interface UserReadUseCase {
    User findSourceById(Long sourceId);

    User findTargetById(Long targetId);
}
