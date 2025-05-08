package co.cboadam.pago_consumer.listener;

import co.cboadam.pago_consumer.model.FormularioTicket;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PagoListener {

    private final RestTemplate restTemplate = new RestTemplate();

    @RabbitListener(queues = "pago.request.queue")
    public void procesarPago(String ticketId) {
        System.out.println("💸 Procesando pago para el ticket ID: " + ticketId);

        try {
            // Obtener el ticket desde el microservicio ticket-consumer
            String url = "http://localhost:8081/api/ticket/" + ticketId;
            FormularioTicket ticket = restTemplate.getForObject(url, FormularioTicket.class);

            if (ticket != null) {
                ticket.setPagado(true); // Simular que se pagó

                // Enviar la actualización al microservicio original (opcional)
                restTemplate.put("http://localhost:8081/api/ticket/" + ticketId, ticket);

                System.out.println("Ticket pagado: " + ticket.getNombre());
            } else {
                System.out.println("Ticket no encontrado con ID: " + ticketId);
            }
        } catch (Exception e) {
            System.out.println("Error procesando el pago: " + e.getMessage());
        }
    }
}
