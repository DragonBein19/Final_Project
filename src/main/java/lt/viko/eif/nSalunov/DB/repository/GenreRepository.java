package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Genre entities.
 * Extends JpaRepository to provide CRUD operations and pagination support.
 */

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}