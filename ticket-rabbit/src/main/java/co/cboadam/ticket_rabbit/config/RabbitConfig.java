package co.cboadam.ticket_rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String TICKET_QUEUE = "ticket.request.queue";
    public static final String TICKET_EXCHANGE = "ticket.direct";

    @Bean
    public DirectExchange ticketExchange() {
        return new DirectExchange(TICKET_EXCHANGE);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public Queue ticketQueue() {
        return new Queue(TICKET_QUEUE, true);
    }

    @Bean
    public Binding bindingTicket() {
        return BindingBuilder.bind(ticketQueue()).to(ticketExchange()).with("ticket.request");
    }
}
