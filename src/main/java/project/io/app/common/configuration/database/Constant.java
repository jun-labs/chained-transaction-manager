package project.io.app.common.configuration.database;

public interface Constant {
    // ChainedTransactionManager
    String CHAINED_TRANSACTION_MANAGER = "chainedTransactionManager";
    String REVERSE_CHAINED_TRANSACTION_MANAGER = "reverseChainedTransactionManager";

    // Source
    String SOURCE_ENTITY_MANAGER_FACTORY_BEAN = "sourceEntityManagerFactoryBean";
    String SOURCE_ENTITY_MANAGER_FACTORY = "sourceEntityManagerFactory";
    String SOURCE_ENTITY_MANAGER = "sourceEntityManager";
    String SOURCE_TRANSACTION_MANAGER = "sourceTransactionManager";
    String SOURCE_DATA_SOURCE_INITIALIZER = "sourceDataSourceInitializer";
    String SOURCE_DATA_SOURCE = "sourceDataSource";
    String SOURCE_JPA_QUERY_FACTORY = "sourceJpaQueryFactory";

    // Target
    String TARGET_ENTITY_MANAGER_FACTORY_BEAN = "targetEntityManagerFactoryBean";
    String TARGET_ENTITY_MANAGER_FACTORY = "targetEntityManagerFactory";
    String TARGET_ENTITY_MANAGER = "targetEntityManager";
    String TARGET_TRANSACTION_MANAGER = "targetTransactionManager";
    String TARGET_DATA_SOURCE_INITIALIZER = "targetDataSourceInitializer";
    String TARGET_DATA_SOURCE = "targetDataSource";
    String TARGET_JPA_QUERY_FACTORY = "targetJpaQueryFactory";
}
