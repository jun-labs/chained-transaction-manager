package project.io.app.integrationtest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import project.io.app.core.user.application.service.UserReadService;
import project.io.app.core.user.domain.User;
import project.io.app.core.user.facade.UserFacade;

@SpringBootTest
@ActiveProfiles("test")
class ChainedTransactionManagerIntegrationTest {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private UserReadService userReadService;

    @Test
    @DisplayName("에러가 발생하지 않는다면 모든 엔티티가 저장된다.")
    void shouldBeSavedBothEntities() {
        User source = new User("Source");
        User target = new User("Target");

        userFacade.saveUsersWithChainedTransactionManager(source, target);

        assertNotNull(userReadService.findSourceById(source.getId()));
        assertNotNull(userReadService.findTargetById(target.getId()));
    }

    @Test
    @DisplayName("ChainedTransactionManager에 등록하더라도 Propagation으로 인해 롤백이 되지 않을 수 있다.")
    void shouldNotBeRollbackWithPropagationTest() {
        User source = new User("Source");
        User target = new User("Target");

        assertThrows(RuntimeException.class, () -> {
            userFacade.saveUsersWithChainedTransactionManagerFailure(source, target);
        });

        assertNotNull(userReadService.findSourceById(source.getId()));
    }

    @Test
    @DisplayName("ChainedTransactionManager에 등록되는 반대 순으로 커밋/롤백이 진행된다.")
    void rollbackOrderLogCheckTest() {
        User source = new User("Source");
        User target = new User("Target");

        assertThrows(RuntimeException.class,
            () -> userFacade.saveUsersWithReverseChainedTransactionManager(source, target)
        );
    }
}
