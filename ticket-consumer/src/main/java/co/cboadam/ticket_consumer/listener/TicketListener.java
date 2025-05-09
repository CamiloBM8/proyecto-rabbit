package co.cboadam.ticket_consumer.listener;

import co.cboadam.ticket_consumer.model.FormularioTicket;
import co.cboadam.ticket_consumer.repository.TicketRepository;
import co.cboadam.ticket_consumer.service.NotificacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TicketListener {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private TicketRepository ticketRepository;

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "ticket.request.queue")
    public void procesarFormulario(FormularioTicket ticket) {
        System.out.println("🎫 Recibida solicitud de " + ticket.getNombre() + " para zona " + ticket.getZona());

        ticket.setId(UUID.randomUUID().toString());
        String asientoAsignado = "Zona-" + ticket.getZona() + "-A" + (int)(Math.random() * 100 + 1);
        ticket.setAsiento(asientoAsignado);
        ticket.setPagado(false);

        ticketRepository.save(ticket);

        String linkPago = "http://localhost:8080/pagar?id=" + ticket.getId();

        notificacionService.enviarCorreo(
                ticket.getCorreo(),
                "Tu entrada para " + ticket.getZona(),
                "Hola " + ticket.getNombre() + ",\n\nTu asiento asignado es: " + asientoAsignado +
                        "\nHaz clic para pagar: " + linkPago);
    }
}
