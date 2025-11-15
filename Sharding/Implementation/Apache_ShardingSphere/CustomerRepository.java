package Sharding.Implementation.Apache_ShardingSphere;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Regular JPA methods - ShardingSphere handles the routing
    List<Customer> findByNameContaining(String nameFragment);
}