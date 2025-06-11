package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing Artist entities from the database.
 * Extends JpaRepository to provide CRUD operations and pagination support.
 */

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
