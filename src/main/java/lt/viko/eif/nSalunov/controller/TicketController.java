package lt.viko.eif.nSalunov.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lt.viko.eif.nSalunov.DB.model.Ticket;
import lt.viko.eif.nSalunov.DB.repository.TicketRepository;


@RestController
@RequestMapping("/ticket")
@CrossOrigin
public class TicketController {
    private final TicketRepository ticketRepository;

    public TicketController (TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

        @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // GET ticket by ID
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        return ticket.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }

    // POST: create new ticket
    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    // DELETE ticket by ID
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
