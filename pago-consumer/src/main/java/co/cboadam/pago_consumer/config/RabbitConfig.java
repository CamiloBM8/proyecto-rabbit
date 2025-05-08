package co.cboadam.pago_consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PAGO_QUEUE = "pago.request.queue";
    public static final String PAGO_EXCHANGE = "pago.direct";

    @Bean
    public DirectExchange pagoExchange() {
        return new DirectExchange(PAGO_EXCHANGE);
    }

    @Bean
    public Queue pagoQueue() {
        return new Queue(PAGO_QUEUE, true);
    }

    @Bean
    public Binding bindingPago() {
        // Si no usas routingKey explícita, usas el mismo nombre de la cola como default
        return BindingBuilder.bind(pagoQueue()).to(pagoExchange()).with(PAGO_QUEUE);
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
}
