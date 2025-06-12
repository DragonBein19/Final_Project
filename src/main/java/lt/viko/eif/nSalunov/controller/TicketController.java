package lt.viko.eif.nSalunov.controller;

import jakarta.transaction.Transactional;
import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.OrderEntity;
import lt.viko.eif.nSalunov.DB.model.Ticket;
import lt.viko.eif.nSalunov.DB.model.TicketCategory;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.OrderRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketCategoryRepository;
import lt.viko.eif.nSalunov.DB.repository.TicketRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * Controller class for managing tickets in the system.
 * <p>
 * Provides RESTful endpoints to perform CRUD operations on {@link Ticket},
 * and retrieve related {@link Concert} and {@link TicketCategory} data.
 * </p>
 */

@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {

    private final TicketRepository ticketRepository;
    private final ConcertRepository concertRepository;
    private final TicketCategoryRepository ticketCategoryRepository;
    private final OrderRepository orderRepository;

    public TicketController(
            TicketRepository ticketRepository,
            ConcertRepository concertRepository,
            TicketCategoryRepository ticketCategoryRepository,
            OrderRepository orderRepository
    ) {
        this.ticketRepository = ticketRepository;
        this.concertRepository = concertRepository;
        this.ticketCategoryRepository = ticketCategoryRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Ticket ticket : tickets) {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", ticket.getId());
            dto.put("category", ticket.getCategory());
            dto.put("concert_name", ticket.getConcert() != null ? ticket.getConcert().getConcertName() : null);
            dto.put("seat_number", ticket.getSeatNumber());
            dto.put("status", ticket.getStatus());

            TicketCategory category = ticket.getTicketCategory();
            dto.put("ticket_category_description", category != null ? category.getDescription() : null);
            dto.put("price", category != null ? category.getPrice() : null);

            result.add(dto);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/categories")
    public ResponseEntity<?> getAllTicketCategories() {
        List<TicketCategory> categories = ticketCategoryRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (TicketCategory category : categories) {
            Map<String, Object> dto = new HashMap<>();
            dto.put("description", category.getDescription());
            dto.put("price", category.getPrice());
            result.add(dto);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createTicket(@RequestBody Map<String, Object> body) {
        try {
            String category = (String) body.get("category");
            String seat = (String) body.get("seat_number");
            String status = (String) body.get("status");
            String concertName = (String) body.get("concert_name");
            String ticketDescription = (String) body.get("ticket_category_description");

            Optional<Concert> concertOpt = concertRepository.findByConcertName(concertName);
            List<TicketCategory> ticketCatList = ticketCategoryRepository.findAllByDescription(ticketDescription);

            if (concertOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid concert name: " + concertName);
            }

            if (ticketCatList.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid ticket category: " + ticketDescription);
            } else if (ticketCatList.size() > 1) {
                return ResponseEntity.badRequest().body("Ambiguous ticket category: multiple entries found for '" + ticketDescription + "'");
            }

            TicketCategory cat = ticketCatList.get(0);

            Ticket ticket = new Ticket();
            ticket.setCategory(category);
            ticket.setSeatNumber(seat);
            ticket.setStatus(status);
            ticket.setConcert(concertOpt.get());
            ticket.setTicketCategory(cat);

            Ticket saved = ticketRepository.save(ticket);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating ticket: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTicket(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketOpt.get();

        try {
            ticket.setCategory((String) body.get("category"));
            ticket.setSeatNumber((String) body.get("seat_number"));
            ticket.setStatus((String) body.get("status"));

            // Update concert if provided
            String concertName = (String) body.get("concert_name");
            if (concertName != null) {
                concertRepository.findByConcertName(concertName).ifPresent(ticket::setConcert);
            }

            // Update ticket category if provided
            String ticketDesc = (String) body.get("ticket_category_description");
            if (ticketDesc != null) {
                List<TicketCategory> matches = ticketCategoryRepository.findAllByDescription(ticketDesc);

                if (matches.isEmpty()) {
                    return ResponseEntity.badRequest().body("Ticket category not found: " + ticketDesc);
                } else if (matches.size() > 1) {
                    return ResponseEntity.badRequest().body("Ambiguous ticket category: multiple entries with description '" + ticketDesc + "'");
                }

                TicketCategory cat = matches.get(0);

                // Update price if provided
                Object priceObj = body.get("price");
                if (priceObj != null) {
                    try {
                        BigDecimal newPrice = new BigDecimal(priceObj.toString());
                        cat.setPrice(newPrice);
                        ticketCategoryRepository.save(cat);
                    } catch (NumberFormatException e) {
                        return ResponseEntity.badRequest().body("Invalid price format.");
                    }
                }

                ticket.setTicketCategory(cat);
            }

            Ticket updated = ticketRepository.save(ticket);
            return ResponseEntity.ok(updated);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating ticket: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketOpt.get();

        // Remove ticket from all orders
        List<OrderEntity> orders = orderRepository.findAll();
        for (OrderEntity order : orders) {
            if (order.getTickets().remove(ticket)) {
                orderRepository.save(order); // persist the change
            }
        }

        ticketRepository.delete(ticket);
        return ResponseEntity.ok("Ticket deleted.");
    }
}
