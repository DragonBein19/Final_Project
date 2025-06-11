package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Genre;
import lt.viko.eif.nSalunov.DB.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Seeder component responsible for populating the database with initial Genre data.
 * <p>
 * This class checks if the genre table is empty and, if so,
 * inserts a predefined set of music genres.
 * </p>
 */

@Component
public class GenreSeeder {

    private final GenreRepository genreRepository;

    @Autowired
    public GenreSeeder(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public void seedGenres() {
        if (genreRepository.count() == 0) {
            genreRepository.save(new Genre("Rock"));
            genreRepository.save(new Genre("Pop"));
            genreRepository.save(new Genre("Jazz"));
            genreRepository.save(new Genre("Classical"));

            System.out.println("Genre duomenys įrašyti į DB.");
        }
    }
}
