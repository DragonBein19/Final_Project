package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing Users entities.
 * Provides CRUD operations and custom query methods for Users.
 */

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    boolean existsByEmail(String email);

    Optional<Users> findByUserNameAndPassword(String userName, String password);

    boolean existsByUserName(String userName);
}
