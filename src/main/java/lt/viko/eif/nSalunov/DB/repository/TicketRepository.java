package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Ticket entities.
 * Provides CRUD operations and query method support for Ticket.
 */

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
