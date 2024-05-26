package project.io.app.core.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static project.io.app.common.configuration.database.Constant.SOURCE_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_TRANSACTION_MANAGER;
import project.io.app.core.user.application.UserReadUseCase;
import project.io.app.core.user.domain.User;
import project.io.app.core.user.domain.UserReadRepository;
import project.io.app.core.user.exception.UserNotFoundException;

@Service
@RequiredArgsConstructor
public class UserReadService implements UserReadUseCase {

    private final UserReadRepository userReadRepository;

    @Override
    @Transactional(
        readOnly = true,
        value = SOURCE_TRANSACTION_MANAGER
    )
    public User findSourceById(Long sourceId) {
        return userReadRepository.findSourceById(sourceId)
            .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(
        readOnly = true,
        value = TARGET_TRANSACTION_MANAGER
    )
    public User findTargetById(Long targetId) {
        return userReadRepository.findTargetById(targetId)
            .orElseThrow(UserNotFoundException::new);
    }
}
