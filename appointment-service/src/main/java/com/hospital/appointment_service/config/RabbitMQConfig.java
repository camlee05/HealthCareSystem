package com.hospital.appointment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public RabbitMQConfig() {
        System.out.println("🐇 RabbitMQConfig loaded!");
    }

    // 💬 Tên các thành phần
    public static final String EXCHANGE_NAME = "hospital.exchange";
    public static final String QUEUE_NAME = "appointment.notifications";
    public static final String ROUTING_KEY = "appointment.created";

    // 🔹 Tạo Exchange (Topic để định tuyến message)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    // 🔹 Tạo Queue (hàng đợi nhận message)
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true); // durable = true -> không mất khi restart
    }

    // 🔹 Liên kết Queue và Exchange qua Routing Key
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
