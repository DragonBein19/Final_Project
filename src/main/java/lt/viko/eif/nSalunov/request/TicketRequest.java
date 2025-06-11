package lt.viko.eif.nSalunov.request;

/**
 * Represents a request to create or update a ticket.
 * 
 * Contains details about the ticket such as category, seat number, status,
 * and associations to concert and ticket category by their IDs.
 */

public class TicketRequest {
    private String category;
    private String seat_number;
    private String status;
    private Long concert_id;
    private Long ticket_category_id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSeat_number() {
        return seat_number;
    }

    public void setSeat_number(String seat_number) {
        this.seat_number = seat_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getConcert_id() {
        return concert_id;
    }

    public void setConcert_id(Long concert_id) {
        this.concert_id = concert_id;
    }

    public Long getTicket_category_id() {
        return ticket_category_id;
    }

    public void setTicket_category_id(Long ticket_category_id) {
        this.ticket_category_id = ticket_category_id;
    }
}
