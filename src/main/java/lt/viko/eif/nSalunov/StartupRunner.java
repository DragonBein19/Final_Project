package lt.viko.eif.nSalunov;

import lt.viko.eif.nSalunov.DB.repository.TicketRepository;
import lt.viko.eif.nSalunov.DB.seeder.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Runs at application startup to seed initial data into the database.
 * <p>
 * This class implements CommandLineRunner and executes the seed methods
 * of various Seeder components to populate the database with initial data
 * such as genres, artists, users, venues, concerts, ticket categories, and tickets.
 */

@Component
public class StartupRunner implements CommandLineRunner {

    private final ArtistSeeder artistSeeder;
    private final GenreSeeder genreSeeder;
    private final UserSeeder userSeeder;
    private final VenueSeeder venueSeeder;
    private final ConcertSeeder concertSeeder;
    private final TicketCategorySeeder ticketCategorySeeder;
    private final TicketSeeder ticketSeeder;

    public StartupRunner(
            ArtistSeeder artistSeeder,
            GenreSeeder genreSeeder,
            UserSeeder userSeeder,
            VenueSeeder venueSeeder,
            ConcertSeeder concertSeeder,
            TicketCategorySeeder ticketCategorySeeder,
            TicketSeeder ticketSeeder) {
        this.artistSeeder = artistSeeder;
        this.genreSeeder = genreSeeder;
        this.userSeeder = userSeeder;
        this.venueSeeder = venueSeeder;
        this.concertSeeder = concertSeeder;
        this.ticketCategorySeeder = ticketCategorySeeder;
        this.ticketSeeder = ticketSeeder;
    }

    @Override
    public void run(String... args) {
        genreSeeder.seedGenres();
        artistSeeder.seedArtists();
        userSeeder.seedUsers();
        venueSeeder.seedVenue();
        concertSeeder.seedConcert();
        ticketCategorySeeder.SeedTicketCategory();
        ticketSeeder.SeedTicket();
    }
}
