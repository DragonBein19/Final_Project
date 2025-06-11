package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Concert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Concert entities.
 * Provides standard CRUD operations via JpaRepository.
 */

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
