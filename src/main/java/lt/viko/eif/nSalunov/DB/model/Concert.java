package lt.viko.eif.nSalunov.DB.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "concert_date", nullable = false)
    private LocalDateTime concert_date;

    @Column(name = "tickets_sold")
    private int ticketsSold = 0;

    @Column(name = "tickets_limit", nullable = false)
    private int ticketsLimit;

    @Column(nullable = false, length = 45)
    private String status;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "venue_id", nullable = false)
    @JsonManagedReference
    private Venue venue;

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

    public Concert(String concertName, LocalDateTime concert_date, int ticketsLimit,
                   String status, String description, Venue venue) {
        this.concertName = concertName;
        this.concert_date = concert_date;
        this.ticketsLimit = ticketsLimit;
        this.status = status;
        this.description = description;
        this.venue = venue;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getConcertName() { return concertName; }
    public void setConcertName(String concertName) { this.concertName = concertName; }

    public LocalDateTime getConcert_date() { return concert_date; }
    public void setConcert_date(LocalDateTime concert_date) { this.concert_date = concert_date; }

    public int getTicketsSold() { return ticketsSold; }
    public void setTicketsSold(int ticketsSold) { this.ticketsSold = ticketsSold; }

    public int getTicketsLimit() { return ticketsLimit; }
    public void setTicketsLimit(int ticketsLimit) { this.ticketsLimit = ticketsLimit; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status;}

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Venue getVenue() { return venue; }
    public void setVenue(Venue venue) { this.venue = venue; }

    public Set<Artist> getArtists() { return artists; }
    public void setArtists(Set<Artist> artists) { this.artists = artists; }

    public Set<TicketCategory> getTicketCategories() { return ticketCategories; }
    public void setTicketCategories(Set<TicketCategory> ticketCategories) { this.ticketCategories = ticketCategories; }
}