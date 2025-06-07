package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Venue;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.VenuesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class ConcertSeeder {
    private final ConcertRepository concertRepository;
    private final VenuesRepository venuesRepository;

    @Autowired
    public ConcertSeeder(ConcertRepository concertRepository, VenuesRepository venuesRepository) {
        this.concertRepository = concertRepository;
        this.venuesRepository = venuesRepository;
    }

    public void seedConcert() {
        if(concertRepository.count() == 0)
        {
            LocalDate date = LocalDate.of(2025, 5, 5);
            LocalTime time = LocalTime.of(0, 0);

            Venue Vilnius = venuesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"));

            Concert concert1 = new Concert(
                    "ImagenDragon",
                    LocalDateTime.of(date, time),
                    100,
                    "active",
                    "Description",
                    Vilnius
            );

            concertRepository.save(concert1);
        }
    }
}
