package Sharding.Implementation.Apache_ShardingSphere;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    public Page<Customer> findAllCustomers(int page, int size) {
        // ShardingSphere automatically merges and orders results from all shards
        return customerRepository.findAll(PageRequest.of(page, size, Sort.by("id")));
    }
}