package project.io.app.core.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static project.io.app.common.configuration.database.Constant.SOURCE_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_TRANSACTION_MANAGER;
import project.io.app.core.user.application.UserWriteUseCase;
import project.io.app.core.user.domain.User;
import project.io.app.core.user.domain.UserWriteRepository;

@Service
@RequiredArgsConstructor
public class UserWriteService implements UserWriteUseCase {

    private final UserWriteRepository userWriteRepository;

    @Override
    @Transactional(
        value = SOURCE_TRANSACTION_MANAGER,
        propagation = Propagation.REQUIRES_NEW
    )
    public Long saveSource(User source) {
        User newSource = userWriteRepository.saveSource(source);
        return newSource.getId();
    }

    @Override
    @Transactional(value = SOURCE_TRANSACTION_MANAGER)
    public Long saveSourceWithFailure(User source) {
        userWriteRepository.saveSource(source);
        throw new RuntimeException();
    }

    @Override
    @Transactional(
        value = TARGET_TRANSACTION_MANAGER,
        propagation = Propagation.REQUIRES_NEW
    )
    public Long saveTarget(User target) {
        User newTarget = userWriteRepository.saveTarget(target);
        return newTarget.getId();
    }

    @Override
    @Transactional(value = TARGET_TRANSACTION_MANAGER)
    public Long saveTargetWithFailure(User target) {
        userWriteRepository.saveTarget(target);
        throw new RuntimeException();
    }
}
