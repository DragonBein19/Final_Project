package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    // Patikrina, ar vartotojas su tokiu el. paštu egzistuoja
    boolean existsByEmail(String email);

    // Naudojama prisijungimui – grąžina vartotoją pagal username ir password
    Optional<Users> findByUserNameAndPassword(String userName, String password);

    boolean existsByUserName(String userName);

}
