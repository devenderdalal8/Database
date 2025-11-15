package Replication.Implementation.RoutingDataSource;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource writeDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://primary-db:5432/app")
                .username("app_user")
                .password("secret")
                .build();
    }

    @Bean
    public DataSource readDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://replica-db:5432/app")
                .username("app_user")
                .password("secret")
                .build();
    }

    @Bean
    @Primary
    public DataSource routingDataSource(
            @Qualifier("writeDataSource") DataSource writeDataSource,
            @Qualifier("readDataSource") DataSource readDataSource) {

        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("WRITE", writeDataSource);
        dataSources.put("READ", readDataSource);

        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);

        return routingDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource routingDataSource) {
        return new DataSourceTransactionManager(routingDataSource);
    }
    // Make this our primary DataSource that the application will use
    @Primary
    @Bean
    public DataSource dataSource() {
        // LazyConnectionDataSourceProxy improves performance by only
        // connecting to the database when actually needed
        return new LazyConnectionDataSourceProxy(routingDataSource());
    }
}