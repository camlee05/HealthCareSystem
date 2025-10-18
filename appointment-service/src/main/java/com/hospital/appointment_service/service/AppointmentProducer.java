package com.hospital.appointment_service.service;

import com.hospital.appointment_service.config.RabbitMQConfig;
import com.hospital.appointment_service.event.AppointmentEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {

    private final RabbitTemplate rabbitTemplate;

    public AppointmentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendAppointmentEvent(AppointmentEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
        System.out.println("📤 Gửi JSON event: " + event);
    }
}
