package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Artist;
import lt.viko.eif.nSalunov.DB.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistSeeder {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistSeeder(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public void seedArtists() {
        if (artistRepository.count() == 0) {
            artistRepository.save(new Artist(1, "Queen", "UK"));
            artistRepository.save(new Artist(2, "AC/DC", "Australia"));
            artistRepository.save(new Artist(3, "ABBA", "Sweden"));

            System.out.println("Artist duomenys įrašyti į DB.");
        }
    }
}
