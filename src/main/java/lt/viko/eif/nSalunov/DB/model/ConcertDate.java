package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Entity representing the specific date and time an artist will perform at a concert.
 */

@Entity
@Table(name = "concert_date")
public class ConcertDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    /*@OneToMany(mappedBy = "concertDate", cascade = CascadeType.ALL)
    private Set<TicketCategory> ticketCategories;*/

    @ManyToOne
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    public ConcertDate() {
    }

    public ConcertDate(LocalDate date, LocalDateTime time, Concert concert) {
        this.date = date;
        this.time = time;
        this.concert = concert;
        //this.ticketCategories = ticketCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Concert getConcert() {
        return concert;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }

    /*public Set<TicketCategory> getTicketCategories() { return ticketCategories; }
    public void setTicketCategories(Set<TicketCategory> ticketCategories) { this.ticketCategories = ticketCategories; }*/
}