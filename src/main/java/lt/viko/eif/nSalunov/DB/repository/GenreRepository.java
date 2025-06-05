package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}