package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Venue entities.
 * Extends JpaRepository to provide CRUD operations on Venue.
 */

@Repository
public interface VenuesRepository extends JpaRepository<Venue, Long> {
}
