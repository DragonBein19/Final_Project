package gtestN;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Venue;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.VenuesRepository;
import lt.viko.eif.nSalunov.controller.ConcertController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConcertTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private VenuesRepository venuesRepository;

    @InjectMocks
    private ConcertController concertController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------
    // 1. GET ALL CONCERTS
    // ------------------------

    @Test
    void testGetConcerts_ReturnsList() {
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setConcertName("Rock Fest");
        concert.setConcert_date(LocalDateTime.now());
        concert.setDescription("Big event");
        concert.setTicketsLimit(5000);
        concert.setTicketsSold(1000);
        concert.setStatus("Scheduled");

        when(concertRepository.findAll()).thenReturn(List.of(concert));

        ResponseEntity<?> response = concertController.getConcerts();

        assertEquals(200, response.getStatusCodeValue());
        List<?> list = (List<?>) response.getBody();
        assertEquals(1, list.size());
    }

    @Test
    void testGetConcerts_Empty() {
        when(concertRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = concertController.getConcerts();
        assertEquals(200, response.getStatusCodeValue());

        List<?> list = (List<?>) response.getBody();
        assertTrue(list.isEmpty());
    }

    // ------------------------
    // 2. SEARCH CONCERTS
    // ------------------------

    @Test
    void testSearchConcertsByName_ReturnsMatch() {
        Concert c = new Concert();
        c.setId(1L);
        c.setConcertName("Jazz Night");

        when(concertRepository.findAll()).thenReturn(List.of(c));

        ResponseEntity<?> response = concertController.searchConcertsByName("jazz");
        List<?> list = (List<?>) response.getBody();

        assertEquals(1, list.size());
    }

    @Test
    void testSearchConcertsByName_NoMatch() {
        Concert c = new Concert();
        c.setConcertName("Metal Fest");

        when(concertRepository.findAll()).thenReturn(List.of(c));

        ResponseEntity<?> response = concertController.searchConcertsByName("pop");
        List<?> list = (List<?>) response.getBody();

        assertEquals(0, list.size());
    }

    // ------------------------
    // 3. UPDATE CONCERT
    // ------------------------

    @Test
    void testUpdateConcert_Success() {
        Concert c = new Concert();
        c.setId(1L);
        c.setConcertName("Old Name");

        when(concertRepository.findById(1L)).thenReturn(Optional.of(c));

        Map<String, Object> updates = Map.of("concertName", "New Name");

        ResponseEntity<?> response = concertController.updateConcert(1L, updates);

        assertEquals(200, response.getStatusCodeValue());
        verify(concertRepository, times(1)).save(c);
        assertEquals("New Name", c.getConcertName());
    }

    @Test
    void testUpdateConcert_NotFound() {
        when(concertRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = concertController.updateConcert(1L, Map.of());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdateConcert_InvalidDateFormat() {
        Concert c = new Concert();
        c.setId(1L);

        when(concertRepository.findById(1L)).thenReturn(Optional.of(c));

        Map<String, Object> updates = Map.of("Concert date", "INVALID_DATE");

        ResponseEntity<?> response = concertController.updateConcert(1L, updates);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("Update failed"));
    }

    @Test
    void testUpdateConcert_UpdateAllFields() {
        Concert c = new Concert();
        c.setId(1L);

        when(concertRepository.findById(1L)).thenReturn(Optional.of(c));

        Map<String, Object> updates = new HashMap<>();
        updates.put("Concert name", "Mega Fest");
        updates.put("Concert date", "2025-01-05T16:00:00");
        updates.put("description", "Updated desc");
        updates.put("Ticket limit", 777);
        updates.put("Ticket Sold", 123);
        updates.put("status", "Finished");

        ResponseEntity<?> response = concertController.updateConcert(1L, updates);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Mega Fest", c.getConcertName());
        assertEquals("Updated desc", c.getDescription());
        assertEquals(777, c.getTicketsLimit());
        assertEquals(123, c.getTicketsSold());
        assertEquals("Finished", c.getStatus());
    }

    // ------------------------
    // 4. CREATE CONCERT
    // ------------------------

    @Test
    void testCreateConcert_Success() {
        Venue venue = new Venue();
        venue.setId(5L);

        when(venuesRepository.findById(5L)).thenReturn(Optional.of(venue));
        when(concertRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Map<String, Object> venueMap = Map.of("id", 5L);

        Map<String, Object> body = new HashMap<>();
        body.put("concertName", "RockFest");
        body.put("concert_date", "2025-01-01T12:00:00");
        body.put("description", "Mega event");
        body.put("status", "Scheduled");
        body.put("ticketsLimit", 4000);
        body.put("ticketsSold", 0);
        body.put("venue", venueMap);

        ResponseEntity<?> response = concertController.createConcert(body);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateConcert_InvalidVenueId() {
        when(venuesRepository.findById(99L)).thenReturn(Optional.empty());

        Map<String, Object> body = new HashMap<>();
        body.put("concertName", "Test");
        body.put("concert_date", "2025-01-01T12:00:00");
        body.put("description", "Desc");
        body.put("status", "Scheduled");
        body.put("ticketsLimit", 100);
        body.put("ticketsSold", 10);
        body.put("venue", Map.of("id", 99L));

        ResponseEntity<?> response = concertController.createConcert(body);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testCreateConcert_BadDate() {
        Venue venue = new Venue();
        venue.setId(1L);

        when(venuesRepository.findById(1L)).thenReturn(Optional.of(venue));

        Map<String, Object> body = new HashMap<>();
        body.put("concertName", "Test");
        body.put("concert_date", "INVALID_DATE");
        body.put("description", "Desc");
        body.put("status", "Scheduled");
        body.put("ticketsLimit", 100);
        body.put("ticketsSold", 10);
        body.put("venue", Map.of("id", 1L));

        ResponseEntity<?> response = concertController.createConcert(body);

        assertEquals(500, response.getStatusCodeValue());
    }

    // ------------------------
    // 5. DELETE CONCERT
    // ------------------------

    @Test
    void testDeleteConcert_Success() {
        when(concertRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<?> response = concertController.deleteConcert(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(concertRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteConcert_NotFound() {
        when(concertRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<?> response = concertController.deleteConcert(1L);

        assertEquals(404, response.getStatusCodeValue());
    }
}
