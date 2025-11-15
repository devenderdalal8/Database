package Sharding.Implementation.Apache_ShardingSphere;

/**
 * Service that handles creating a Customer and an associated Order.
 * Designed for a sharded environment — the transaction spans the involved shards.
 * Uses Spring-managed repositories injected via @Autowired.
 */
@Service
public class CustomerOrderService {
    
    // Repository for Customer entities (injected by Spring)
    @Autowired
    private CustomerRepository customerRepository;
    
    // Repository for Order entities (injected by Spring)
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * Create a customer and an order in a single transactional operation.
     * The customer is saved first to obtain its generated id, which is then
     * set on the order before saving the order. rollbackFor = Exception.class
     * ensures the whole operation is rolled back on any exception.
     *
     * @param customer the customer to persist
     * @param order the order to persist and associate with the customer
     */
    @Transactional(rollbackFor = Exception.class)
    public void createCustomerWithOrder(Customer customer, Order order) {
        // Save customer to generate id
        customerRepository.save(customer);
        // Associate order with the saved customer and save order
        order.setCustomerId(customer.getId());
        orderRepository.save(order);
    }
}
