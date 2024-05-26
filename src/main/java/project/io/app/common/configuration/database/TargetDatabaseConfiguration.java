package project.io.app.common.configuration.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import static project.io.app.common.configuration.database.Constant.TARGET_DATA_SOURCE;
import static project.io.app.common.configuration.database.Constant.TARGET_DATA_SOURCE_INITIALIZER;
import static project.io.app.common.configuration.database.Constant.TARGET_ENTITY_MANAGER;
import static project.io.app.common.configuration.database.Constant.TARGET_ENTITY_MANAGER_FACTORY;
import static project.io.app.common.configuration.database.Constant.TARGET_ENTITY_MANAGER_FACTORY_BEAN;
import static project.io.app.common.configuration.database.Constant.TARGET_TRANSACTION_MANAGER;

@Configuration
@EnableJpaRepositories(
    basePackages = "project.io.app.core",
    entityManagerFactoryRef = "targetEntityManagerFactory",
    transactionManagerRef = "targetTransactionManager"
)
public class TargetDatabaseConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.target")
    public DataSource targetDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = TARGET_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean targetEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(targetDataSource());
        factoryBean.setPackagesToScan("project.io.app.core");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }

    @Bean(name = TARGET_ENTITY_MANAGER_FACTORY)
    public EntityManagerFactory targetEntityManagerFactory() {
        return targetEntityManagerFactoryBean().getObject();
    }

    @Bean(name = TARGET_ENTITY_MANAGER)
    public EntityManager targetEntityManager() {
        return SharedEntityManagerCreator.createSharedEntityManager(targetEntityManagerFactory());
    }

    @Bean(name = TARGET_TRANSACTION_MANAGER)
    public JpaTransactionManager targetTransactionManager() {
        return new CustomJpaTransactionManager(targetEntityManagerFactory(), "Target");
    }

    @Bean(name = TARGET_DATA_SOURCE_INITIALIZER)
    public DataSourceInitializer targetDataSourceInitializer(
        @Qualifier(TARGET_DATA_SOURCE)
        DataSource dataSource
    ) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema/schema.sql")));
        return initializer;
    }
}
