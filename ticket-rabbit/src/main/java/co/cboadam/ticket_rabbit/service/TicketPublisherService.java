package co.cboadam.ticket_rabbit.service;

import co.cboadam.ticket_rabbit.model.FormularioTicket;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TicketPublisherService {

    private static final String EXCHANGE = "ticket.direct";
    private static final String ROUTING_KEY = "ticket.request";

    private static final String PAGO_EXCHANGE = "pago.direct";
    private static final String PAGO_ROUTING_KEY = "pago.request";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RestTemplate restTemplate;

    public void publicarSolicitud(FormularioTicket ticket) {
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, ticket);
    }

    public void enviarPago(String id) {
        rabbitTemplate.convertAndSend(PAGO_EXCHANGE, PAGO_ROUTING_KEY, id);
    }

    public FormularioTicket obtenerTicketPorId(String id) {
        return restTemplate.getForObject("http://localhost:8081/api/ticket/" + id, FormularioTicket.class);
    }
}
