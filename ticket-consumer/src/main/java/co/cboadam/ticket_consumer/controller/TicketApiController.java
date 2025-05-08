package co.cboadam.ticket_consumer.controller;

import co.cboadam.ticket_consumer.model.FormularioTicket;
import co.cboadam.ticket_consumer.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
public class TicketApiController {

    @Autowired
    private TicketRepository repository;

    @GetMapping("/{id}")
    public FormularioTicket obtenerPorId(@PathVariable String id) {
        Optional<FormularioTicket> ticket = repository.findById(id);
        return ticket.orElse(null);
    }

    @PutMapping("/{id}")
    public FormularioTicket marcarComoPagado(@PathVariable String id) {
        return repository.findById(id).map(ticket -> {
            ticket.setPagado(true);
            return repository.save(ticket);
        }).orElse(null);
    }
}
