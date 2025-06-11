package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.Optional;
=======
/**
 * Repository interface for managing Concert entities.
 * Provides standard CRUD operations via JpaRepository.
 */
>>>>>>> 0b8723e088b4a63fa5376083abf18d36e90a03c7

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
    Optional<Concert> findByConcertName(String concertName);
}
