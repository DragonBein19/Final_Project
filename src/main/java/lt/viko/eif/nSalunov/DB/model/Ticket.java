package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_description_id", nullable = false)
    private int ticketDescriptionId;

    @Column(name = "concert_id", nullable = false)
    private int concertId;

    @Column(nullable = false, length = 45)
    private String category;

    @Column(name = "seat_number", nullable = false, length = 45)
    private String seatNumber;

    @Column(nullable = false, length = 45)
    private String status;

    public Ticket() {
    }

    public Ticket(int ticketDescriptionId, int concertId, String category, String seatNumber, String status) {
        this.ticketDescriptionId = ticketDescriptionId;
        this.concertId = concertId;
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

    public int getTicketDescriptionId() {
        return ticketDescriptionId;
    }

    public void setTicketDescriptionId(int ticketDescriptionId) {
        this.ticketDescriptionId = ticketDescriptionId;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
