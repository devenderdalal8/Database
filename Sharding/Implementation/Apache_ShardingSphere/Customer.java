package Sharding.Implementation.Apache_ShardingSphere;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    private Long id;
    private String name;
    private String email;
    // Getters and setters
}
