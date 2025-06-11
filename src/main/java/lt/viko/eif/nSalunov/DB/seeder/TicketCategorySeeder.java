package lt.viko.eif.nSalunov.DB.seeder;

import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import lt.viko.eif.nSalunov.DB.repository.TicketCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class TicketCategorySeeder {

    private final TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    public TicketCategorySeeder(TicketCategoryRepository ticketCategoryRepository){
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    public void SeedTicketCategory(){
        if(ticketCategoryRepository.count() == 0)
        {
            TicketCategory ticketCategory1 = new TicketCategory(
                    "Description",
                    BigDecimal.valueOf(59, 99),
                    LocalDate.of(2025, 5, 5),
                    LocalDate.of(2025, 5, 5),
                    "Vilnius"
            );

            TicketCategory ticketCategory2 = new TicketCategory(
                    "Description",
                    BigDecimal.valueOf(59, 99),
                    LocalDate.of(2025, 5, 5),
                    LocalDate.of(2025, 5, 5),
                    "Vilnius"
            );

            TicketCategory ticketCategory3 = new TicketCategory(
                    "Description",
                    BigDecimal.valueOf(59, 99),
                    LocalDate.of(2025, 5, 5),
                    LocalDate.of(2025, 5, 5),
                    "Vilnius"
            );

            ticketCategoryRepository.save(ticketCategory1);
            ticketCategoryRepository.save(ticketCategory2);
            ticketCategoryRepository.save(ticketCategory3);


            System.out.println("👥 Ticket category seeded.");
        }
    }
}
