package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name="concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="venue_id", nullable= false)
    private int venueId;

    @Column(name = "bands_id", nullable = false)
    private int bandsId;

    @Column(name = "concert_date_id", nullable = false)
    private int concertDateId;

    @Column(name = "concert_name", nullable = false)
    private String concertName;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(name = "tickets_sold")
    private int ticketsSold = 0;

    @Column(name = "tickets_limit", nullable = false)
    private int ticketsLimit;

    @Column(nullable = false, length = 45)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    public Concert() {
    }

    public Concert(int venueId, int bandsId, int concertDateId, String concertName,
                   LocalDateTime time, int ticketsLimit, String status, String description) {
        this.venueId = venueId;
        this.bandsId = bandsId;
        this.concertDateId = concertDateId;
        this.concertName = concertName;
        this.time = time;
        this.ticketsLimit = ticketsLimit;
        this.status = status;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVenueId() {
        return venueId;
    }

    public void setVenueId(int venueId) {
        this.venueId = venueId;
    }

    public int getConcertDateId() {
        return concertDateId;
    }

    public void setConcertDateId(int concertDateId) {
        this.concertDateId = concertDateId;
    }

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public int getTicketsLimit() {
        return ticketsLimit;
    }

    public void setTicketsLimit(int ticketsLimit) {
        this.ticketsLimit = ticketsLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}