package lt.viko.eif.nSalunov.request;

import java.time.LocalDateTime;

/**
 * Represents a request payload for creating or updating a Concert.
 * 
 * Contains all necessary fields to describe a concert including its name, date, description,
 * status, ticket limits and sales, and the associated venue.
 */

public class ConcertRequest {
    private String concertName;
    private LocalDateTime concert_date;
    private String description;
    private String status;
    private int ticketsLimit;
    private int ticketsSold;
    private Long venueId;

    public String getConcertName() {
        return concertName;
    }

    public void setConcertName(String concertName) {
        this.concertName = concertName;
    }

    public LocalDateTime getConcert_date() {
        return concert_date;
    }

    public void setConcert_date(LocalDateTime concert_date) {
        this.concert_date = concert_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTicketsLimit() {
        return ticketsLimit;
    }

    public void setTicketsLimit(int ticketsLimit) {
        this.ticketsLimit = ticketsLimit;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public Long getVenueId() {
        return venueId;
    }

    public void setVenueId(Long venueId) {
        this.venueId = venueId;
    }
}
