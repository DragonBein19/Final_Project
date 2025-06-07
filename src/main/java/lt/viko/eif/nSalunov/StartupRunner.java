package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.seeder.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ArtistSeeder artistSeeder;
    private final GenreSeeder genreSeeder;
    private final UserSeeder userSeeder;
    private final VenueSeeder venueSeeder;
    private final ConcertSeeder concertSeeder;

    public StartupRunner(
            ArtistSeeder artistSeeder,
            GenreSeeder genreSeeder,
            UserSeeder userSeeder,
            VenueSeeder venueSeeder,
            ConcertSeeder concertSeeder) {
        this.artistSeeder = artistSeeder;
        this.genreSeeder = genreSeeder;
        this.userSeeder = userSeeder;
        this.venueSeeder = venueSeeder;
        this.concertSeeder = concertSeeder;
    }

    @Override
    public void run(String... args) {
        genreSeeder.seedGenres();
        artistSeeder.seedArtists();
        userSeeder.seedUsers();
        venueSeeder.seedVenue();
        concertSeeder.seedConcert();
    }
}
