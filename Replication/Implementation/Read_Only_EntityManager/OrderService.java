package Replication.Implementation.Read_Only_EntityManager;

import java.util.List;

/*
 * All reads go through the readOnlyEntityManager, 
 * which hits the replica. Any new order or update goes through the 
 * writeEntityManager, which connects to the primary.
 * That works without any context switching or proxies. 
 * You just write your queries as usual, and the injected entity manager does the 
 * rest. It’s clearer what each method does, and you avoid having to rely 
 * on any external annotation or AOP behavior to decide which database gets hit. 
 * It’s also worth noting that this setup can be helpful for tests. 
 * You can mock each manager separately, which gives you better control over 
 * what part of the system is doing reads and what part is doing writes.
 */
@Service
public class OrderService {

    @PersistenceContext(unitName = "read")
    private EntityManager readOnlyEntityManager;

    @PersistenceContext(unitName = "write")
    private EntityManager writeEntityManager;

    /*
     * Read operations use the read-only EntityManagerConfig
     */
    @Transactional(readOnly = true, transactionManager = "readTransactionManager")
    public List<Order> getAllOrders() {
        return readOnlyEntityManager
                .createQuery("SELECT o FROM Order o", Order.class)
                .getResultList();
    }

    @Transactional
    public void saveOrder(Order order) {
        writeEntityManager.persist(order);
    }
}
