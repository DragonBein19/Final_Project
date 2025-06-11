package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketCategoryRepository extends JpaRepository<TicketCategory, Long> {

    // Naujasis metodas – leidžia gauti kelis rezultatus
    List<TicketCategory> findAllByDescription(String description);
}
