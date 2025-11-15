package Sharding.Implementation.Custom_Shard_Routing;

/**
 * REST controller that exposes customer-related endpoints.
 * 
 * Note: This class relies on Spring MVC / Spring Web annotations such as
 * @RestController, @RequestMapping, @GetMapping, @Autowired and types like
 * ResponseEntity, PathVariable and EmptyResultDataAccessException.
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    /**
     * Repository used to load Customer entities.
     * It is injected by the framework (Spring) at runtime.
     */
    @Autowired
    private CustomerRepository customerRepository;
    
    /**
     * GET /api/customers/{id}
     *
     * Retrieve a single Customer by its id.
     *
     * @param id the id of the customer to retrieve
     * @return ResponseEntity containing the found Customer and 200 OK, or 404 Not Found
     *         when the customer does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        try {
            // Attempt to load the customer from the repository.
            Customer customer = customerRepository.findById(id);
            // Return 200 OK with the customer in the response body.
            return ResponseEntity.ok(customer);
        } catch (EmptyResultDataAccessException e) {
            // When the repository throws an exception indicating no result,
            // translate it into a 404 Not Found response.
            return ResponseEntity.notFound().build();
        }
    }
}