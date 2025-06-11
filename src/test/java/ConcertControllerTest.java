import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Venue;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.VenuesRepository;
import lt.viko.eif.nSalunov.controller.ConcertController;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for {@link ConcertController}.
 * <p>
 * Configures Spring MVC test infrastructure for the ConcertController, mocking
 * dependencies such as {@link ConcertRepository} and {@link VenuesRepository}.
 * Tests REST endpoints for concert management including retrieval, creation,
 * update, deletion, and search functionality.
 * </p>
 */

@WebMvcTest(ConcertController.class)
@ContextConfiguration(classes = {ConcertController.class, ConcertControllerTest.MockConfig.class})
public class ConcertControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ConcertRepository concertRepository;
    @Autowired private VenuesRepository venuesRepository;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @TestConfiguration
    static class MockConfig {
        @Bean
        public ConcertRepository concertRepository() {
            return Mockito.mock(ConcertRepository.class);
        }

        @Bean
        public VenuesRepository venuesRepository() {
            return Mockito.mock(VenuesRepository.class);
        }
    }

    private Venue venue;
    private Concert concert;

    @BeforeEach
    public void setup() {
        venue = new Venue();
        venue.setId(1L);
        venue.setCity("Vilnius");
        venue.setIndoor(true);

        concert = new Concert();
        concert.setId(1L);
        concert.setConcertName("Test Concert");
        concert.setConcert_date(LocalDateTime.of(2025, 6, 11, 7, 40));
        concert.setTicketsLimit(100);
        concert.setTicketsSold(10);
        concert.setStatus("Active");
        concert.setDescription("Test description");
        concert.setVenue(venue);
    }

    @Test
    public void getConcerts_shouldReturnListWithAllFields() throws Exception {
        when(concertRepository.findAll()).thenReturn(List.of(concert));

        mockMvc.perform(get("/concerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(concert.getId()))
                .andExpect(jsonPath("$[0]['Concert name']").value("Test Concert"))
                .andExpect(jsonPath("$[0]['Concert date']").exists())
                .andExpect(jsonPath("$[0].description").value("Test description"))
                .andExpect(jsonPath("$[0]['Ticket limit']").value(100))
                .andExpect(jsonPath("$[0]['Ticket Sold']").value(10))
                .andExpect(jsonPath("$[0].status").value("Active"));
    }

    @Test
    public void createConcert_shouldReturnFullConcertObject() throws Exception {
        Map<String, Object> request = new HashMap<>();
        request.put("concertName", concert.getConcertName());
        request.put("concert_date", concert.getConcert_date().toString());
        request.put("description", concert.getDescription());
        request.put("status", concert.getStatus());
        request.put("ticketsLimit", concert.getTicketsLimit());
        request.put("ticketsSold", concert.getTicketsSold());

        Map<String, Object> venueMap = new HashMap<>();
        venueMap.put("id", 1L);
        request.put("venue", venueMap);

        when(venuesRepository.findById(1L)).thenReturn(Optional.of(venue));
        when(concertRepository.save(Mockito.any(Concert.class))).thenReturn(concert);

        mockMvc.perform(post("/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.concertName").value("Test Concert"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.ticketsLimit").value(100))
                .andExpect(jsonPath("$.ticketsSold").value(10))
                .andExpect(jsonPath("$.status").value("Active"));
    }

    @Test
    public void updateConcert_shouldReturnOkAndUpdateFields() throws Exception {
        Map<String, Object> updates = new HashMap<>();
        updates.put("Concert name", "Updated Concert");

        Concert updatedConcert = new Concert();
        updatedConcert.setId(concert.getId());
        updatedConcert.setConcertName("Updated Concert");
        updatedConcert.setConcert_date(concert.getConcert_date());
        updatedConcert.setTicketsLimit(100);
        updatedConcert.setTicketsSold(10);
        updatedConcert.setStatus("Active");
        updatedConcert.setDescription("Test description");
        updatedConcert.setVenue(venue);

        when(concertRepository.findById(1L)).thenReturn(Optional.of(concert));
        when(concertRepository.save(Mockito.any(Concert.class))).thenReturn(updatedConcert);

        mockMvc.perform(put("/concerts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteConcert_shouldReturnOk_whenExists() throws Exception {
        when(concertRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/concerts/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteConcert_shouldReturnNotFound_whenDoesNotExist() throws Exception {
        when(concertRepository.existsById(1L)).thenReturn(false);

        mockMvc.perform(delete("/concerts/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchConcertsByName_shouldReturnMatchingConcerts() throws Exception {
        when(concertRepository.findAll()).thenReturn(List.of(concert));

        mockMvc.perform(get("/concerts/search?name=Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['Concert name']").value("Test Concert"));
    }
}
