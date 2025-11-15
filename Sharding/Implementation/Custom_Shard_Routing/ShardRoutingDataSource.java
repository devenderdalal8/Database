package Sharding.Implementation.Custom_Shard_Routing;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

//This class extends Spring’s built-in routing capability to decide which shard to use:



/**
 * ShardRoutingDataSource routes JDBC calls to the correct shard based on a ThreadLocal key.
 *
 * Usage:
 *  - Call ShardRoutingDataSource.setCurrentShard(shardId) before executing DB operations
 *    so the routing data source resolves to the desired shard.
 *  - After the operation (preferably in a finally block) call clearCurrentShard() to avoid
 *    leaking the shard selection to subsequent requests handled by the same thread.
 *
 * Note: Ensure spring-jdbc (which provides AbstractRoutingDataSource) is on the classpath.
 */
public class ShardRoutingDataSource extends AbstractRoutingDataSource {
    // ThreadLocal to hold the shard id for the current thread
    private static final ThreadLocal<String> currentShard = new ThreadLocal<>();

    /**
     * Bind a shard identifier to the current thread.
     * @param shardId identifier of the shard to use, or null to indicate default
     */
    public static void setCurrentShard(String shardId) {
        currentShard.set(shardId);
    }

    /**
     * Retrieve the shard identifier bound to the current thread.
     * @return current shard id, or null if none (default data source)
     */
    public static String getCurrentShard() {
        return currentShard.get();
    }

    /**
     * Clear the shard identifier for the current thread to prevent accidental reuse.
     * Always call this after DB work is finished.
     */
    public static void clearCurrentShard() {
        currentShard.remove();
    }

    /**
     * Called by Spring to determine which data source (shard) should be used for the current lookup.
     * Returns the shard id previously set via setCurrentShard().
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return getCurrentShard();
    }
}