package Replication.Implementation.Read_Only_EntityManager;

import javax.sql.DataSource;

/*
 * This configuration sets up two separate EntityManagers: one for write operations
 * and another for read-only operations. The write EntityManager is marked as primary,
 * ensuring that it is used by default unless specified otherwise.
 */
@Configuration
public class EntityManagerConfig {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean writeEntityManager(
            @Qualifier("writeDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.model")
                .persistenceUnit("write")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean readEntityManager(
            @Qualifier("readDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(dataSource)
                .packages("com.example.model")
                .persistenceUnit("read")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager writeTransactionManager(
            @Qualifier("writeEntityManager") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public PlatformTransactionManager readTransactionManager(
            @Qualifier("readEntityManager") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}