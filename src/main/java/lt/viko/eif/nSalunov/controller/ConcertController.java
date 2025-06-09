package lt.viko.eif.nSalunov.controller;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Venue;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import lt.viko.eif.nSalunov.DB.repository.VenuesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertRepository concertRepository;
    private final VenuesRepository venuesRepository;

    public ConcertController(ConcertRepository concertRepository, VenuesRepository venuesRepository) {
        this.concertRepository = concertRepository;
        this.venuesRepository = venuesRepository;
    }

    @GetMapping
    public ResponseEntity<?> getConcerts() {
        List<Concert> concerts = concertRepository.findAll();
        List<Map<String, Object>> result = concerts.stream().map(concert -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", concert.getId());
            dto.put("Concert name", concert.getConcertName());
            dto.put("Concert date", concert.getConcert_date());
            dto.put("description", concert.getDescription());
            dto.put("Ticket limit", concert.getTicketsLimit());
            dto.put("Ticket Sold", concert.getTicketsSold());
            dto.put("status", concert.getStatus());
            return dto;
        }).toList();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConcert(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return concertRepository.findById(id).map(concert -> {
            try {
                if (updates.containsKey("concertName")) {
                    concert.setConcertName((String) updates.get("concertName"));
                }
                if (updates.containsKey("Concert date")) {
                    concert.setConcert_date(LocalDateTime.parse((String) updates.get("Concert date")));
                }
                if (updates.containsKey("description")) {
                    concert.setDescription((String) updates.get("description"));
                }
                if (updates.containsKey("Ticket limit")) {
                    concert.setTicketsLimit(Integer.parseInt(updates.get("Ticket limit").toString()));
                }
                if (updates.containsKey("Ticket Sold")) {
                    concert.setTicketsSold(Integer.parseInt(updates.get("Ticket Sold").toString()));
                }
                if (updates.containsKey("status")) {
                    concert.setStatus((String) updates.get("status"));
                }
                concertRepository.save(concert);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Update failed: " + e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createConcert(@RequestBody Map<String, Object> body) {
        try {
            String concertName = (String) body.get("concertName");
            String concertDateStr = (String) body.get("concert_date");
            String description = (String) body.get("description");
            String status = (String) body.get("status");
            int ticketsLimit = Integer.parseInt(body.get("ticketsLimit").toString());
            int ticketsSold = Integer.parseInt(body.get("ticketsSold").toString());

            Map<String, Object> venueMap = (Map<String, Object>) body.get("venue");
            Long venueId = Long.parseLong(venueMap.get("id").toString());

            Optional<Venue> venueOpt = venuesRepository.findById(venueId);
            if (venueOpt.isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid venue ID: " + venueId);
            }

            Concert concert = new Concert();
            concert.setConcertName(concertName);
            concert.setConcert_date(LocalDateTime.parse(concertDateStr));
            concert.setDescription(description);
            concert.setStatus(status);
            concert.setTicketsLimit(ticketsLimit);
            concert.setTicketsSold(ticketsSold);
            concert.setVenue(venueOpt.get());

            Concert savedConcert = concertRepository.save(concert);
            return ResponseEntity.ok(savedConcert);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error creating concert: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteConcert(@PathVariable Long id) {
        if (concertRepository.existsById(id)) {
            concertRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
