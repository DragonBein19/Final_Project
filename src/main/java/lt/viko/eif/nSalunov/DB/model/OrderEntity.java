package lt.viko.eif.nSalunov.DB.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_id", nullable = false)
    private int ticketId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "order_description_id", nullable = false)
    private int orderDescriptionId;

    public OrderEntity() {
    }

    public OrderEntity(int ticketId, int userId, int orderDescriptionId) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.orderDescriptionId = orderDescriptionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderDescriptionId() {
        return orderDescriptionId;
    }

    public void setOrderDescriptionId(int orderDescriptionId) {
        this.orderDescriptionId = orderDescriptionId;
    }
}
