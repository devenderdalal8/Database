package Sharding.Implementation.Custom_Shard_Routing;

/**
 * Repository responsible for loading Customer entities from the database.
 *
 * This repository uses a sharding strategy:
 * - The ShardingService determines which shard a given customerId belongs to.
 * - ShardRoutingDataSource is informed of the chosen shard (typically via a ThreadLocal)
 *   so that JdbcTemplate will route the SQL to the correct physical DataSource.
 *
 * Note: This class assumes Spring will inject JdbcTemplate and ShardingService.
 */
@Repository
public class CustomerRepository {
    
    // Spring's helper for executing JDBC operations; expected to be configured
    // against a DataSource that supports routing based on the current shard.
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    // Service that encapsulates the sharding logic (e.g. hashing or range mapping).
    @Autowired
    private ShardingService shardingService;
    
    /**
     * Find a customer by their ID.
     *
     * Behavior:
     * 1. Use the ShardingService to determine which shard contains the customer.
     * 2. Set the current shard on the routing DataSource so subsequent JDBC calls
     *    go to the correct physical database.
     * 3. Execute the query using JdbcTemplate and map the ResultSet to a Customer.
     * 4. Always clear the shard selection in a finally block to avoid leaking state
     *    across threads or requests.
     *
     * @param customerId The customer id to look up (must not be null).
     * @return The matched Customer, or null / exception depending on JdbcTemplate behavior.
     */
    public Customer findById(Long customerId) {
        try {
            // Step 1: Determine which shard contains the requested customer.
            String shardKey = shardingService.determineShardKey(customerId);
            
            // Step 2: Instruct the routing DataSource to use the chosen shard.
            // Typically this sets a ThreadLocal so the routing DataSource can pick
            // the appropriate underlying DataSource for the current thread.
            ShardRoutingDataSource.setCurrentShard(shardKey);
            
            // Step 3: Execute the query on the selected shard. The RowMapper lambda
            // converts a single ResultSet row into a Customer instance.
            return jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE id = ?", 
                new Object[]{customerId},
                (resultSet, rowNum) -> {
                    Customer customer = new Customer();
                    // Map columns from the result set to the Customer object.
                    customer.setId(resultSet.getLong("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setEmail(resultSet.getString("email"));
                    // TODO: map other fields as needed (addresses, created_at, etc.)
                    return customer;
                }
            );
        } finally {
            // Step 4: Always clear the shard selection to prevent the shard state
            // from leaking into subsequent operations on the same thread.
            ShardRoutingDataSource.clearCurrentShard();
        }
    }
}