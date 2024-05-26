package project.io.app.common.configuration.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static project.io.app.common.configuration.database.Constant.SOURCE_ENTITY_MANAGER;
import static project.io.app.common.configuration.database.Constant.SOURCE_JPA_QUERY_FACTORY;
import static project.io.app.common.configuration.database.Constant.TARGET_ENTITY_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_JPA_QUERY_FACTORY;

@Configuration
public class QueryDslConfiguration {

    @Bean(name = SOURCE_JPA_QUERY_FACTORY)
    public JPAQueryFactory sourceJpaQueryFactory(
        @Qualifier(SOURCE_ENTITY_MANAGER)
        EntityManager entityManager
    ) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean(name = TARGET_JPA_QUERY_FACTORY)
    public JPAQueryFactory targetJpaQueryFactory(
        @Qualifier(TARGET_ENTITY_MANAGER) EntityManager entityManager
    ) {
        return new JPAQueryFactory(entityManager);
    }
}
