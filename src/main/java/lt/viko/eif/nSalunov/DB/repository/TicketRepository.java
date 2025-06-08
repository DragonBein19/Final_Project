package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
