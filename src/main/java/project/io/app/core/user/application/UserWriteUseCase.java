package project.io.app.core.user.application;

import project.io.app.core.user.domain.*;

public interface UserWriteUseCase {
    Long saveSource(User source);

    Long saveSourceWithFailure(User source);

    Long saveTarget(User target);

    Long saveTargetWithFailure(User target);
}
