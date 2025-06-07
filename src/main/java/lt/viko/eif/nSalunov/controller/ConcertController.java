package lt.viko.eif.nSalunov.controller;

import lt.viko.eif.nSalunov.DB.model.Concert;
import lt.viko.eif.nSalunov.DB.model.Users;
import lt.viko.eif.nSalunov.DB.repository.ConcertRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
