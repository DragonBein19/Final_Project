package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing TicketCategory entities.
 * Provides basic CRUD operations through JpaRepository.
 */

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory,Long> {
}
