package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link TicketCategory} entities.
 * <p>
 * Extends {@link JpaRepository} to provide standard JPA functionalities.
 * </p>
 */

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {

    List<TicketCategory> findAllByDescription(String description);
}
