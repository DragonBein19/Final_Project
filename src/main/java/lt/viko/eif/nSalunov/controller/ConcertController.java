package lt.viko.eif.nSalunov.controller;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/concerts")
public class ConcertController {

    private final ConcertRepository concertRepository;

    public ConcertController (ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @GetMapping
    public ResponseEntity<?> getConcerts()
    {
        List<Concert> concerts = concertRepository.findAll();
        List<Map<String, Object>> result = concerts.stream().map(concert -> {
            Map<String, Object> dto = new HashMap<>();
            dto.put("id", concert.getId());  
            dto.put("Concert name", concert.getConcertName());
            dto.put("Concert date", concert.getConcert_date());
            dto.put("Ticket limit", concert.getTicketsLimit());
            dto.put("Ticket Sold", concert.getTicketsSold());
            dto.put("status", concert.getStatus());
            dto.put("registrationDate", concert.getConcert_date());
            return dto;
        }).toList();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateConcert(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return concertRepository.findById(id).map(concert -> {
            if (updates.containsKey("Concert name")) {
                concert.setConcertName((String) updates.get("Concert name"));
            }
            if (updates.containsKey("Concert date")) {
                // Parse the string to LocalDateTime
                String dateStr = (String) updates.get("Concert date");
                LocalDateTime date = LocalDateTime.parse(dateStr); // Adjust format if needed
                concert.setConcert_date(date);  // use your existing setter
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
            if (updates.containsKey("registrationDate")) {
                // This probably shouldn't be here since registrationDate duplicates Concert date
                // Or handle similarly if needed
            }
            concertRepository.save(concert);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Concert> createConcert(@RequestBody Concert concert) {
        Concert savedConcert = concertRepository.save(concert);
        return ResponseEntity.ok(savedConcert);
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
