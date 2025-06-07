package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Venue;
import lt.viko.eif.nSalunov.DB.repository.VenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VenueSeeder {

    private final VenuesRepository venuesRepository;

    @Autowired
    public VenueSeeder(VenuesRepository venuesRepository) { this.venuesRepository = venuesRepository; }

    public void seedVenue() {
        if(venuesRepository.count() == 0) {
            Venue Vilnius = new Venue(
                    "=37064793293",
                    "Upes g. 9",
                    "Vilnius",
                    "Lithuania",
                    "5000",
                    true,
                    "Map"
            );

            Venue Kaunas = new Venue(
                    "=37064793293",
                    "Upes g. 9",
                    "Vilnius",
                    "Lithuania",
                    "5000",
                    true,
                    "Map"
            );

            Venue Klaipeda = new Venue(
                    "=37064793293",
                    "Upes g. 9",
                    "Vilnius",
                    "Lithuania",
                    "5000",
                    true,
                    "Map"
            );

            venuesRepository.save(Vilnius);
            venuesRepository.save(Kaunas);
            venuesRepository.save(Klaipeda);

            System.out.println("👥 Venues seeded.");
        }
    }
}
