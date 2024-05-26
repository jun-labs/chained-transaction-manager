package project.io.app.core.user.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static project.io.app.common.configuration.database.Constant.CHAINED_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.REVERSE_CHAINED_TRANSACTION_MANAGER;
import project.io.app.core.user.application.UserWriteUseCase;
import project.io.app.core.user.domain.User;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserWriteUseCase userWriteUseCase;

    @Transactional(
        value = CHAINED_TRANSACTION_MANAGER,
        rollbackFor = Exception.class
    )
    public void saveUsers(
        User source,
        User target
    ) {
        userWriteUseCase.saveSource(source);
        userWriteUseCase.saveTargetWithFailure(target);
    }

    @Transactional(
        value = REVERSE_CHAINED_TRANSACTION_MANAGER,
        rollbackFor = Exception.class
    )
    public void saveUsersWithInvalidChainedTransactionManager(
        User source,
        User target
    ) {
        userWriteUseCase.saveTarget(target);
        userWriteUseCase.saveSourceWithFailure(source);
    }
}
