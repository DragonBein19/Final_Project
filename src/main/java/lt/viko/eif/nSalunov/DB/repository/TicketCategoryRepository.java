package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import java.util.Optional;
=======
/**
 * Repository interface for managing TicketCategory entities.
 * Provides basic CRUD operations through JpaRepository.
 */
>>>>>>> 0b8723e088b4a63fa5376083abf18d36e90a03c7

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {
    Optional<TicketCategory> findByDescription(String description);
}
