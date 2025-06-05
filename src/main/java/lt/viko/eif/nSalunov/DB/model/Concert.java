package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "concerts")
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private Set<ConcertDate> concertDates;

    @ManyToMany
    @JoinTable(
            name = "concert_artists",
            joinColumns = @JoinColumn(name = "concert_id"),
            inverseJoinColumns = @JoinColumn(name = "artist_id")
    )
    private Set<Artist> artists;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL)
    private Set<TicketCategory> ticketCategories;

    public Concert() {
    }

    public Concert(String concertName, LocalDateTime time, int ticketsLimit,
                   String status, String description, Venue venue, Set<ConcertDate> concertDates) {
        this.concertName = concertName;
        this.time = time;
        this.ticketsLimit = ticketsLimit;
        this.status = status;
        this.description = description;
        this.venue = venue;
        this.concertDates = concertDates;
    }

    public Long getId() {
        return id;
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

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public Set<ConcertDate> getConcertDates() {
        return concertDates;
    }

    public void setConcertDates(Set<ConcertDate> concertDates) {
        this.concertDates = concertDates;
    }

    public Set<Artist> getArtists() {
        return artists;
    }

    public void setArtists(Set<Artist> artists) {
        this.artists = artists;
    }

    public Set<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(Set<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }
}