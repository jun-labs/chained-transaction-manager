package project.io.app.common.configuration.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import static project.io.app.common.configuration.database.Constant.CHAINED_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.REVERSE_CHAINED_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.SOURCE_TRANSACTION_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_TRANSACTION_MANAGER;

@Configuration
public class ChainedTransactionConfiguration {

    @Bean(name = CHAINED_TRANSACTION_MANAGER)
    public PlatformTransactionManager chainedTransactionManager(
        @Qualifier(SOURCE_TRANSACTION_MANAGER)
        PlatformTransactionManager sourceTransactionManager,

        @Qualifier(TARGET_TRANSACTION_MANAGER)
        PlatformTransactionManager targetTransactionManager
    ) {
        @SuppressWarnings(value = "deprecation")
        PlatformTransactionManager transactionManager =
            new ChainedTransactionManager(sourceTransactionManager, targetTransactionManager);
        return transactionManager;
    }

    @Bean(name = REVERSE_CHAINED_TRANSACTION_MANAGER)
    public PlatformTransactionManager invalidChainedTransactionManager(
        @Qualifier(SOURCE_TRANSACTION_MANAGER)
        PlatformTransactionManager sourceTransactionManager,

        @Qualifier(TARGET_TRANSACTION_MANAGER)
        PlatformTransactionManager targetTransactionManager
    ) {
        @SuppressWarnings(value = "deprecation")
        PlatformTransactionManager transactionManager =
            new ChainedTransactionManager(targetTransactionManager, sourceTransactionManager);
        return transactionManager;
    }
}

