package Replication.Implementation.RoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource{
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSourceType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return getCurrentShard();
    }
}
