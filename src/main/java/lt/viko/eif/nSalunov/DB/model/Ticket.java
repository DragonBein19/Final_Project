package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

/**
 * Represents a ticket for a concert, which includes information about
 * its category, seat number, status, and the associated concert and ticket category.
 */

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;


    @Column(nullable = false, length = 45)
    private String category;

    @Column(name = "seat_number", nullable = false, length = 45)
    private String seatNumber;

    @Column(nullable = false, length = 45)
    private String status;

    @ManyToOne
    @JoinColumn(name = "ticket_category_id", nullable = false)
    private TicketCategory ticketCategory;

    public Ticket() {
    }

    public Ticket(Concert concert, TicketCategory ticketCategory, String category, String seatNumber, String status) {
        this.concert = concert;
        this.ticketCategory = ticketCategory;
        this.category = category;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Concert getConcert() { return concert; }
    public void setConcert(Concert concert) { this.concert = concert; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public TicketCategory getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(TicketCategory ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

}
