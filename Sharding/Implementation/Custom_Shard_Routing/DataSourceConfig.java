package Sharding.Implementation.Custom_Shard_Routing;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;


/**
 * Central Spring configuration that sets up a sharded DataSource architecture
 * for the application.
 *
 * <p>
 * This configuration:
 * <ul>
 *   <li>Loads DataSource properties for two shards from properties keys
 *       {@code app.datasource.shard1} and {@code app.datasource.shard2}.</li>
 *   <li>Creates concrete DataSource instances for each shard.</li>
 *   <li>Assembles a routing DataSource that delegates to the appropriate shard
 *       at runtime and provides a default/fallback shard.</li>
 *   <li>Exposes a transaction manager backed by the routing DataSource.</li>
 *   <li>Configures a JPA EntityManagerFactory that uses the routing DataSource.</li>
 *   <li>Exposes a primary, lazily-connected DataSource proxy for general
 *       application use (ensures connections are obtained only when needed).</li>
 * </ul>
 * </p>
 *
 * <h3>Beans provided</h3>
 * <ul>
 *   <li>{@code shard1Properties()} - Returns a DataSourceProperties object bound to
 *       {@code app.datasource.shard1}. Use this to configure connection properties
 *       for shard 1 (URL, username, password, driver, pool settings, etc.).</li>
 *
 *   <li>{@code shard2Properties()} - Returns a DataSourceProperties object bound to
 *       {@code app.datasource.shard2}. Use this to configure connection properties
 *       for shard 2.</li>
 *
 *   <li>{@code shard1DataSource()} - Builds and returns a DataSource using the
 *       properties from {@code shard1Properties()}. Represents the physical
 *       connection pool for shard 1.</li>
 *
 *   <li>{@code shard2DataSource()} - Builds and returns a DataSource using the
 *       properties from {@code shard2Properties()}. Represents the physical
 *       connection pool for shard 2.</li>
 *
 *   <li>{@code routingDataSource()} - Constructs and configures a routing
 *       DataSource (custom {@code ShardRoutingDataSource}) whose target map contains
 *       the shard DataSources keyed by identifier (e.g. "shard1", "shard2").
 *       Also sets a default/fallback target DataSource to ensure a predictable
 *       fallback behavior.</li>
 *
 *   <li>{@code transactionManager()} - Exposes a PlatformTransactionManager
 *       (DataSourceTransactionManager) that delegates to the routing DataSource so
 *       that transactions execute against the selected shard.</li>
 *
 *   <li>{@code entityManagerFactory(EntityManagerFactoryBuilder)} - Configures
 *       the JPA EntityManagerFactory to use the routing DataSource, sets the
 *       package(s) to scan for entities, and assigns a persistence unit name
 *       appropriate for a sharded environment.</li>
 *
 *   <li>{@code dataSource()} - Declares the application primary DataSource as a
 *       LazyConnectionDataSourceProxy that wraps the routing DataSource. This
 *       improves performance by deferring actual JDBC connections until required
 *       and makes the routing transparent to consumers that simply inject a
 *       {@code DataSource}.</li>
 * </ul>
 *
 * <h3>Notes and considerations</h3>
 * <ul>
 *   <li>The routing implementation (the custom {@code ShardRoutingDataSource})
 *       is responsible for determining which shard key to use for a given
 *       operation. Ensure that the routing resolution mechanism (e.g., ThreadLocal
 *       context, tenant resolver, or request-scoped strategy) is implemented and
 *       used consistently throughout the application.</li>
 *
 *   <li>Because a single EntityManagerFactory and transaction manager are used,
 *       transactions and JPA operations must be aware of the routing semantics.
 *       Cross-shard transactions are not automatically supported by plain
 *       DataSource routing and will require a distributed transaction strategy
 *       if needed.</li>
 *
 *   <li>Define the shard connection properties (URLs, credentials, pool settings)
 *       in the application's configuration (application.properties / YAML)
 *       under the keys {@code app.datasource.shard1.*} and
 *       {@code app.datasource.shard2.*}.</li>
 *
 *   <li>Consider connection pool sizing and monitoring separately for each
 *       shard to avoid resource contention and to enable independent scaling.</li>
 * </ul>
 *
 * @see org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
 * @see org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
 * @see org.springframework.jdbc.datasource.DataSourceTransactionManager
 * @see org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
 */
@Configuration
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties("app.datasource.shard1")
    public DataSourceProperties shard1Properties() {
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("app.datasource.shard2")
    public DataSourceProperties shard2Properties() {
        return new DataSourceProperties();
    }

    // Create actual connections to each database shard
    @Bean
    public DataSource shard1DataSource() {
        return shard1Properties().initializeDataSourceBuilder().build();
    }

    @Bean
    public DataSource shard2DataSource() {
        return shard2Properties().initializeDataSourceBuilder().build();
    }

    // Create a routing datasource that will switch between shards
    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> dataSources = new HashMap<>();
        dataSources.put("shard1", shard1DataSource());
        dataSources.put("shard2", shard2DataSource());
        
        ShardRoutingDataSource routingDataSource = new ShardRoutingDataSource();
        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(shard1DataSource()); // Fallback shard
        
        return routingDataSource;
    }

     // Set up transaction management
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(routingDataSource());
    }

    // Configure the entity manager factory for JPA
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(routingDataSource())
                .packages("com.example.model") // Your entity package
                .persistenceUnit("shardedPersistenceUnit")
                .build();
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
