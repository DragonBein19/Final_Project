package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a ticket pricing category that defines the price,
 * availability period, and related venue for a group of tickets.
 */

@Entity
@Table(name = "ticket_category")
public class TicketCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(length = 255)
    private String venue;

    /*@ManyToOne
    @JoinColumn(name = "concert_id", nullable = true)
    private Concert concert;*/

    /*@ManyToOne
    @JoinColumn(name = "concert_date_id", nullable = true)
    private ConcertDate concertDate;*/

    @OneToMany(mappedBy = "ticketCategory", cascade = CascadeType.ALL)
    private Set<Ticket> tickets;

    public TicketCategory() {}

    public TicketCategory(String description, BigDecimal price, LocalDate startDate,
                          LocalDate endDate, String venue) {
        this.description = description;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    /*public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }*/

    /*public ConcertDate getConcertDate() { return concertDate; }
    public void setConcertDate(ConcertDate concertDate) { this.concertDate = concertDate; } */

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
