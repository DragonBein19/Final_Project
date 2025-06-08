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
    private LocalDate date;
    private LocalTime time;

    @Autowired
    public ConcertSeeder(ConcertRepository concertRepository, VenuesRepository venuesRepository) {
        this.concertRepository = concertRepository;
        this.venuesRepository = venuesRepository;
    }

    public void seedConcert() {
        if(concertRepository.count() == 0)
        {
            Concert concert1 = new Concert(
                    "ImagineDragon",
                    LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(0, 0)),
                    100,
                    "active",
                    "Description",
                    venuesRepository.findById(1L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );

            Concert concert2 = new Concert(
                    "Justin Timberlake",
                    LocalDateTime.of(LocalDate.of(2025, 06, 8), LocalTime.of(0, 0)),
                    50000,
                    "active",
                    "Description",
                    venuesRepository.findById(2L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );
            Concert concert3 = new Concert(
                    "Free Finga",
                    LocalDateTime.of(LocalDate.of(2025, 11, 11), LocalTime.of(0, 0)),
                    100,
                    "active",
                    "Description",
                    venuesRepository.findById(3L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );

            concertRepository.save(concert1);
            concertRepository.save(concert2);
            concertRepository.save(concert3);

            System.out.println("👥 Concerts seeded.");
        }
    }
}
