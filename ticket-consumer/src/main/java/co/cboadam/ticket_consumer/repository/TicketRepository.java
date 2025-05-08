package co.cboadam.ticket_consumer.repository;

import co.cboadam.ticket_consumer.model.FormularioTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<FormularioTicket, String> {
}