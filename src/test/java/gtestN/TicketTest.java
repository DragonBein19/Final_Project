package gtestN;

import lt.viko.eif.nSalunov.DB.model.*;
import lt.viko.eif.nSalunov.DB.repository.*;
import lt.viko.eif.nSalunov.controller.TicketController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private TicketCategoryRepository ticketCategoryRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------
    // 1. GET ALL TICKETS
    // ------------------------

    @Test
    void testGetAllTickets_ReturnsTickets() {
        TicketCategory cat = new TicketCategory();
        cat.setDescription("VIP");
        cat.setPrice(BigDecimal.TEN);

        Concert concert = new Concert();
        concert.setConcertName("RockFest");

        Ticket t = new Ticket();
        t.setId(1L);
        t.setCategory("A");
        t.setStatus("available");
        t.setSeatNumber("5");
        t.setTicketCategory(cat);
        t.setConcert(concert);

        when(ticketRepository.findAll()).thenReturn(List.of(t));

        ResponseEntity<?> response = ticketController.getAllTickets();
        List<?> list = (List<?>) response.getBody();

        assertEquals(1, list.size());
    }

    @Test
    void testGetAllTickets_EmptyList() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());
        ResponseEntity<?> response = ticketController.getAllTickets();
        assertEquals(0, ((List<?>) response.getBody()).size());
    }

    // ------------------------
    // 2. GET TICKET CATEGORIES
    // ------------------------

    @Test
    void testGetAllTicketCategories() {
        TicketCategory cat = new TicketCategory();
        cat.setDescription("Regular");
        cat.setPrice(BigDecimal.ONE);

        when(ticketCategoryRepository.findAll()).thenReturn(List.of(cat));

        ResponseEntity<?> response = ticketController.getAllTicketCategories();
        List<?> list = (List<?>) response.getBody();

        assertEquals(1, list.size());
    }

    // ------------------------
    // 3. CREATE TICKET
    // ------------------------

    @Test
    void testCreateTicket_Success() {
        Map<String, Object> body = Map.of(
                "category", "VIP",
                "seat_number", "10",
                "status", "available",
                "concert_name", "RockFest",
                "ticket_category_description", "VIP Ticket"
        );

        Concert concert = new Concert();
        concert.setConcertName("RockFest");

        TicketCategory cat = new TicketCategory();
        cat.setDescription("VIP Ticket");

        when(concertRepository.findByConcertName("RockFest"))
                .thenReturn(Optional.of(concert));

        when(ticketCategoryRepository.findAllByDescription("VIP Ticket"))
                .thenReturn(List.of(cat));

        when(ticketRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        ResponseEntity<?> response = ticketController.createTicket(body);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateTicket_InvalidConcert() {
        Map<String, Object> body = Map.of("concert_name", "Unknown");

        when(concertRepository.findByConcertName("Unknown"))
                .thenReturn(Optional.empty());

        ResponseEntity<?> response = ticketController.createTicket(body);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreateTicket_CategoryNotFound() {
        Map<String, Object> body = Map.of(
                "concert_name", "Rock",
                "ticket_category_description", "DoesNotExist"
        );

        Concert c = new Concert();
        c.setConcertName("Rock");

        when(concertRepository.findByConcertName("Rock"))
                .thenReturn(Optional.of(c));

        when(ticketCategoryRepository.findAllByDescription("DoesNotExist"))
                .thenReturn(List.of());

        ResponseEntity<?> response = ticketController.createTicket(body);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Invalid ticket category"));
    }

    @Test
    void testCreateTicket_CategoryAmbiguous() {
        Map<String, Object> body = Map.of(
                "concert_name", "Rock",
                "ticket_category_description", "VIP"
        );

        Concert c = new Concert();
        c.setConcertName("Rock");

        when(concertRepository.findByConcertName("Rock"))
                .thenReturn(Optional.of(c));

        TicketCategory cat = new TicketCategory();
        when(ticketCategoryRepository.findAllByDescription("VIP"))
                .thenReturn(List.of(cat, cat));

        ResponseEntity<?> response = ticketController.createTicket(body);
        assertEquals(400, response.getStatusCodeValue());
    }

    // ------------------------
    // 4. UPDATE TICKET
    // ------------------------

    @Test
    void testUpdateTicket_Success() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        TicketCategory cat = new TicketCategory();
        cat.setDescription("VIP");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketCategoryRepository.findAllByDescription("VIP")).thenReturn(List.of(cat));
        when(ticketRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Map<String, Object> body = Map.of("ticket_category_description", "VIP");

        ResponseEntity<?> response = ticketController.updateTicket(1L, body);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTicket_NotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = ticketController.updateTicket(1L, Map.of());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTicket_CategoryMissing() {
        Ticket t = new Ticket();
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(t));
        when(ticketCategoryRepository.findAllByDescription("VIP")).thenReturn(List.of());

        Map<String, Object> body = Map.of("ticket_category_description", "VIP");

        ResponseEntity<?> response = ticketController.updateTicket(1L, body);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdateTicket_InvalidPrice() {
        Ticket t = new Ticket();

        TicketCategory cat = new TicketCategory();
        cat.setDescription("VIP");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(t));
        when(ticketCategoryRepository.findAllByDescription("VIP"))
                .thenReturn(List.of(cat));

        Map<String, Object> body = new HashMap<>();
        body.put("ticket_category_description", "VIP");
        body.put("price", "notANumber");

        ResponseEntity<?> response = ticketController.updateTicket(1L, body);
        assertEquals(400, response.getStatusCodeValue());
    }

    // ------------------------
    // 5. DELETE TICKET
    // ------------------------

    @Test
    void testDeleteTicket_Success() {
        Ticket t = new Ticket();
        t.setId(1L);

        OrderEntity order = new OrderEntity();
        order.setTickets(new HashSet<>(Set.of(t)));

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(t));
        when(orderRepository.findAll()).thenReturn(List.of(order));

        ResponseEntity<?> response = ticketController.deleteTicket(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteTicket_NotFound() {
        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = ticketController.deleteTicket(1L);
        assertEquals(404, response.getStatusCodeValue());
    }
}
