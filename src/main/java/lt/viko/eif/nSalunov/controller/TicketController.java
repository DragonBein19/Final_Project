package lt.viko.eif.nSalunov.controller;

import java.util.*;
import java.util.stream.Collectors;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Ticket;
import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import lt.viko.eif.nSalunov.DB.repository.TicketRepository;
import lt.viko.eif.nSalunov.request.TicketRequest;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketCategoryRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing tickets.
 * <p>
 * Provides endpoints to create, read, update, and delete tickets, as well as fetch all tickets.
 * </p>
 */

@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;
    private final TicketCategoryRepository ticketCategoryRepository;

    public TicketController(TicketRepository ticketRepository, ConcertRepository concertRepository, TicketCategoryRepository ticketCategoryRepository) {
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
    }

    @GetMapping
    public List<Map<String, Object>> getAllTickets() {
        return ticketRepository.findAll().stream().map(ticket -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", ticket.getId());
            dto.put("category", ticket.getCategory());
            dto.put("seat_number", ticket.getSeatNumber());
            dto.put("status", ticket.getStatus());

            Concert concert = ticket.getConcert();
            TicketCategory category = ticket.getTicketCategory();

            dto.put("concert_id", concert != null ? concert.getId() : null);
            dto.put("concert_name", concert != null ? concert.getConcertName() : null);

            dto.put("ticket_category_id", category != null ? category.getId() : null);
            dto.put("ticket_category_description", category != null ? category.getDescription() : null);

            return dto;
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        return ticketRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody TicketRequest request) {
        return ticketRepository.findById(id).map(ticket -> {
            try {
                ticket.setCategory(request.getCategory());
                ticket.setSeatNumber(request.getSeat_number());
                ticket.setStatus(request.getStatus());

                Concert concert = new Concert();
                concert.setId(request.getConcert_id());
                ticket.setConcert(concert);

                if (request.getTicket_category_id() != null) {
                    TicketCategory category = new TicketCategory();
                    category.setId(request.getTicket_category_id());
                    ticket.setTicketCategory(category);
                }

                ticketRepository.save(ticket);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error: " + e.getMessage());
            }
        }).orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Map<String, Object> body) {
        try {
            Ticket ticket = new Ticket();

            ticket.setCategory((String) body.get("category"));
            ticket.setSeatNumber((String) body.get("seat_number"));
            ticket.setStatus((String) body.get("status"));

            Long concertId = Long.parseLong(body.get("concert_id").toString());
            concertRepository.findById(concertId).ifPresent(ticket::setConcert);

            if (body.containsKey("ticket_category_id")) {
                Long catId = Long.parseLong(body.get("ticket_category_id").toString());
                ticketCategoryRepository.findById(catId).ifPresent(ticket::setTicketCategory);
            }

            Ticket saved = ticketRepository.save(ticket);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Creation failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        if (ticketRepository.existsById(id)) {
            ticketRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
