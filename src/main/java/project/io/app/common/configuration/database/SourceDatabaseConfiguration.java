package project.io.app.common.configuration.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import static project.io.app.common.configuration.database.Constant.SOURCE_DATA_SOURCE;
import static project.io.app.common.configuration.database.Constant.SOURCE_DATA_SOURCE_INITIALIZER;
import static project.io.app.common.configuration.database.Constant.SOURCE_ENTITY_MANAGER;
import static project.io.app.common.configuration.database.Constant.SOURCE_ENTITY_MANAGER_FACTORY;
import static project.io.app.common.configuration.database.Constant.SOURCE_ENTITY_MANAGER_FACTORY_BEAN;
import static project.io.app.common.configuration.database.Constant.SOURCE_TRANSACTION_MANAGER;

@Configuration
@EnableJpaRepositories(
    basePackages = "project.io.app.core",
    entityManagerFactoryRef = SOURCE_ENTITY_MANAGER_FACTORY,
    transactionManagerRef = SOURCE_TRANSACTION_MANAGER
)
public class SourceDatabaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.source")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = SOURCE_ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean sourceEntityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(sourceDataSource());
        factoryBean.setPackagesToScan("project.io.app.core");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factoryBean;
    }

    @Bean(name = SOURCE_ENTITY_MANAGER_FACTORY)
    @Primary
    public EntityManagerFactory sourceEntityManagerFactory() {
        return sourceEntityManagerFactoryBean().getObject();
    }

    @Bean(name = SOURCE_ENTITY_MANAGER)
    public EntityManager sourceEntityManager() {
        return SharedEntityManagerCreator.createSharedEntityManager(sourceEntityManagerFactory());
    }

    @Bean(name = SOURCE_TRANSACTION_MANAGER)
    @Primary
    public JpaTransactionManager sourceTransactionManager() {
        return new CustomJpaTransactionManager(sourceEntityManagerFactory(), "Source");
    }

    @Bean(name = SOURCE_DATA_SOURCE_INITIALIZER)
    public DataSourceInitializer sourceDataSourceInitializer(
        @Qualifier(SOURCE_DATA_SOURCE)
        DataSource dataSource
    ) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema/schema.sql")));
        return initializer;
    }
}
