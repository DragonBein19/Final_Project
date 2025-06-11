import com.fasterxml.jackson.databind.ObjectMapper;
import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Ticket;
import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketCategoryRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketRepository;
import lt.viko.eif.nSalunov.controller.TicketController;
import lt.viko.eif.nSalunov.request.TicketRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link TicketController}.
 * <p>
 * Sets up Spring MVC test environment with mocked repositories for Tickets, Concerts, and Ticket Categories.
 * Covers basic CRUD operations: fetching all tickets, fetching by ID, creating, updating, and deleting tickets.
 * </p>
 */

@WebMvcTest(TicketController.class)
@ContextConfiguration(classes = {TicketController.class, TicketControllerTest.MockConfig.class})
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TicketCategoryRepository ticketCategoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Ticket ticket;
    private Concert concert;
    private TicketCategory category;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public TicketRepository ticketRepository() {
            return Mockito.mock(TicketRepository.class);
        }

        @Bean
        public ConcertRepository concertRepository() {
            return Mockito.mock(ConcertRepository.class);
        }

        @Bean
        public TicketCategoryRepository ticketCategoryRepository() {
            return Mockito.mock(TicketCategoryRepository.class);
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeEach
    public void setup() {
        concert = new Concert();
        concert.setId(1L);
        concert.setConcertName("Concert Test");

        category = new TicketCategory();
        category.setId(2L);
        category.setDescription("VIP");

        ticket = new Ticket();
        ticket.setId(1L);
        ticket.setCategory("Standard");
        ticket.setSeatNumber("A1");
        ticket.setStatus("Available");
        ticket.setConcert(concert);
        ticket.setTicketCategory(category);
    }

    @Test
    public void getAllTickets_shouldReturnList() throws Exception {
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        mockMvc.perform(get("/ticket"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].category").value("Standard"))
                .andExpect(jsonPath("$[0].seat_number").value("A1"))
                .andExpect(jsonPath("$[0].concert_name").value("Concert Test"))
                .andExpect(jsonPath("$[0].ticket_category_description").value("VIP"));
    }

    @Test
    public void getTicketById_shouldReturnOk() throws Exception {
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/ticket/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Standard"));
    }

    @Test
    public void updateTicket_shouldSucceed() throws Exception {
        TicketRequest request = new TicketRequest();
        request.setCategory("Updated Category");
        request.setSeat_number("B2");
        request.setStatus("Booked");
        request.setConcert_id(1L);
        request.setTicket_category_id(2L);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(put("/ticket/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void createTicket_shouldSucceed() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("category", "Standard");
        body.put("seat_number", "C3");
        body.put("status", "Available");
        body.put("concert_id", 1L);
        body.put("ticket_category_id", 2L);

        when(concertRepository.findById(1L)).thenReturn(Optional.of(concert));
        when(ticketCategoryRepository.findById(2L)).thenReturn(Optional.of(category));
        when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        mockMvc.perform(post("/ticket")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTicket_shouldSucceed() throws Exception {
        when(ticketRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/ticket/1"))
                .andExpect(status().isOk());
    }
}
