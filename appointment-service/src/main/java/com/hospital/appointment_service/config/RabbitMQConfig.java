package com.hospital.appointment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // --- Các constant để tái sử dụng ---
    public static final String EXCHANGE_NAME = "hospital.exchange";
    public static final String QUEUE_NAME = "appointment.notifications";
    public static final String ROUTING_KEY = "appointment.created";

    // --- Tạo Exchange ---
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // --- Tạo Queue ---
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    // --- Binding Queue và Exchange qua Routing Key ---
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }
}
