package User;
@Entity
@Table(name = "users" , indexes = {
    @Index(name = "idx_name", columnList = "firstName"),
    @Index(name = "idx_email", columnList = "email", unique = true)
    @Index(name = "idx_FullName", columnList = "lastName,firstName")
})
class User{
@Id 
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "firstName", nullable = false)
private String firstName;
@Column(name = "email", nullable = false, unique = true)
private String email;
@Column(name = "lastName")
private String lastName;

}