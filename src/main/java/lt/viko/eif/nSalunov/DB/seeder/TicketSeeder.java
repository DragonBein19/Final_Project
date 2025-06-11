package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.Ticket;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketCategoryRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketSeeder {
    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;
    private final TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    public TicketSeeder(TicketRepository ticketRepository, ConcertRepository concertRepository, TicketCategoryRepository ticketCategoryRepository) {
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    public void SeedTicket() {
        if(ticketRepository.count() == 0)
        {
            Ticket ticket1 = new Ticket(
                    concertRepository.findById(1L).orElseThrow(() ->  new RuntimeException("Concert with ID 1 not exist")),
                    ticketCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ticket category with ID 1 not exist")),
                    "Category ^^",
                    "A45",
                    "Active"
            );

            Ticket ticket2 = new Ticket(
                    concertRepository.findById(1L).orElseThrow(() ->  new RuntimeException("Concert with ID 1 not exist")),
                    ticketCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ticket category with ID 1 not exist")),
                    "Category ^^",
                    "A01",
                    "Active"
            );

            Ticket ticket3 = new Ticket(
                    concertRepository.findById(1L).orElseThrow(() ->  new RuntimeException("Concert with ID 1 not exist")),
                    ticketCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ticket category with ID 1 not exist")),
                    "Category ^^",
                    "A44",
                    "Active"
            );

            Ticket ticket4 = new Ticket(
                    concertRepository.findById(1L).orElseThrow(() ->  new RuntimeException("Concert with ID 1 not exist")),
                    ticketCategoryRepository.findById(1L).orElseThrow(() -> new RuntimeException("Ticket category with ID 1 not exist")),
                    "Category ^^",
                    "A80",
                    "Active"
            );

            ticketRepository.save(ticket1);
            ticketRepository.save(ticket2);
            ticketRepository.save(ticket3);
            ticketRepository.save(ticket4);

            System.out.println("👥 Tickets seeded.");
        }
    }

}
