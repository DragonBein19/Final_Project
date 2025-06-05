package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.seeder.ArtistSeeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ArtistSeeder artistSeeder;

    public StartupRunner(ArtistSeeder artistSeeder) {
        this.artistSeeder = artistSeeder;
    }

    @Override
    public void run(String... args) {
        artistSeeder.seedArtists(); // čia įrašomi pradiniai atlikėjai
    }
}
