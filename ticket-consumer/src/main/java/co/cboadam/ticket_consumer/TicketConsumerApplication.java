package co.cboadam.ticket_consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class TicketConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketConsumerApplication.class, args);
	}

}
