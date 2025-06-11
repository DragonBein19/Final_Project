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
                    "ImagineDragons",
                    LocalDateTime.of(LocalDate.of(2025, 5, 5), LocalTime.of(0, 0)),
                    30000,
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
                    LocalDateTime.of(LocalDate.of(2025, 03, 26), LocalTime.of(0, 0)),
                    100,
                    "active",
                    "Description",
                    venuesRepository.findById(3L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );

            Concert concert4 = new Concert(
                    "Jessica Shy",
                    LocalDateTime.of(LocalDate.of(2026, 8, 12), LocalTime.of(0, 0)),
                    10000,
                    "active",
                    "Description",
                    venuesRepository.findById(4L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );

            Concert concert5 = new Concert(
                    "Kendrick Lamar",
                    LocalDateTime.of(LocalDate.of(2026, 04, 12), LocalTime.of(0, 0)),
                    40000,
                    "active",
                    "Description",
                    venuesRepository.findById(5L).orElseThrow(() -> new RuntimeException("Venue with ID 1 not found"))
            );

            concertRepository.save(concert1);
            concertRepository.save(concert2);
            concertRepository.save(concert3);
            concertRepository.save(concert4);
            concertRepository.save(concert5);

            System.out.println("👥 Concerts seeded.");
        }
    }
}
