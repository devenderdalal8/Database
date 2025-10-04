package User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByFirstName(String firstName);
    List<User> findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.lastName = ?1 ORDER BY u.firstName")
    List<User> findUsersByLastNameOrderedByFirstName(String lastName);
}
