package com.hospital.appointment_service.service;

import com.hospital.appointment_service.config.RabbitMQConfig;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class NotificationProducer {

        private static final Logger log = LoggerFactory.getLogger(NotificationProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(String message) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    message
            );
            System.out.println("📤 Sent JSON message to RabbitMQ: " + message);
        } catch (Exception e) {
            System.err.println("⚠️ Failed to send message: " + e.getMessage());
        }
    }
}
