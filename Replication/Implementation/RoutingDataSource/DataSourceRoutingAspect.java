package Replication.Implementation.RoutingDataSource;

/*
 * This means that every method marked with @ReadOnly routes to the replica. 
 * Transactional methods that aren’t read-only route to the write database. 
 * You can control the exact point of routing by placing the annotation on 
 * the service method that triggers the repository call.
 */

@Aspect
@Component
public class DataSourceRoutingAspect {

    @Before("@annotation(ReadOnly)")
    public void markReadOnly() {
        DataSourceContextHolder.setMode("READ");
    }

    @Before("@annotation(tx)")
    public void markTx(Transactional tx) {
        if (tx.readOnly()) {
            DataSourceContextHolder.setMode("READ");
        } else {
            DataSourceContextHolder.setMode("WRITE");
        }
    }

    @After("@annotation(ReadOnly) || @annotation(Transactional)")
    public void clearContext() {
        DataSourceContextHolder.clear();
    }
}