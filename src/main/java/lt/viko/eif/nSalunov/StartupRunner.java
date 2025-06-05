package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.seeder.ArtistSeeder;
import lt.viko.eif.nSalunov.DB.seeder.GenreSeeder;
import lt.viko.eif.nSalunov.DB.seeder.UserSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ArtistSeeder artistSeeder;
    private final GenreSeeder genreSeeder;
    private final UserSeeder userSeeder;

    public StartupRunner(ArtistSeeder artistSeeder, GenreSeeder genreSeeder, UserSeeder userSeeder) {
        this.artistSeeder = artistSeeder;
        this.genreSeeder = genreSeeder;
        this.userSeeder = userSeeder;
    }

    @Override
    public void run(String... args) {
        genreSeeder.seedGenres();
        artistSeeder.seedArtists();
        userSeeder.seedUsers();
    }
}
