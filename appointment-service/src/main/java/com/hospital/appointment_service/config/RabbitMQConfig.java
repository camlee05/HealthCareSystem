package com.hospital.appointment_service.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "notification.exchange";
    public static final String ROUTING_KEY = "notification.key";

    // Producer chỉ cần exchange
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }
}
