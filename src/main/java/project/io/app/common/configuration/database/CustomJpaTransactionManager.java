package project.io.app.common.configuration.database;

import jakarta.persistence.EntityManagerFactory;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

@Slf4j
public class CustomJpaTransactionManager extends JpaTransactionManager {

    private final String name;

    public CustomJpaTransactionManager(
        EntityManagerFactory entityManagerFactory,
        String name
    ) {
        super(entityManagerFactory);
        this.name = name;
    }

    @Override
    protected void doBegin(
        @NonNull Object transaction,
        @NonNull TransactionDefinition definition
    ) {
        log.info("[{}] start transaction.", name);
        super.doBegin(transaction, definition);
    }

    @Override
    protected void doCommit(@NonNull DefaultTransactionStatus status) {
        log.info("[{}] commit transaction.", name);
        super.doCommit(status);
    }

    @Override
    protected void doRollback(@NonNull DefaultTransactionStatus status) {
        log.info("[{}] rollback transaction.", name);
        super.doRollback(status);
    }
}
