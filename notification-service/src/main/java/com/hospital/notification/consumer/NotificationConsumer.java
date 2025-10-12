package com.hospital.notification.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.notification.model.Notification;
import com.hospital.notification.repository.NotificationRepository;
import com.hospital.notification.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationConsumer {

    private final NotificationRepository repo;
    private final ObjectMapper objectMapper;

    public NotificationConsumer(NotificationRepository repo, ObjectMapper objectMapper) {
        this.repo = repo;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleMessage(String message) {
        try {
            System.out.println("📩 Received message from RabbitMQ: " + message);

            JsonNode json = objectMapper.readTree(message);

            String action = json.has("action") ? json.get("action").asText() : "UNKNOWN";
            String msg;

            switch (action) {
                case "CREATE":
                    msg = "📅 New appointment created for patient "
                            + json.get("patientId").asText()
                            + " with doctor " + json.get("doctorId").asText()
                            + " at " + json.get("time").asText();
                    break;
                case "UPDATE":
                    msg = "✏️ Appointment " + json.get("appointmentId").asText()
                            + " updated. Status: " + json.get("status").asText();
                    break;
                case "DELETE":
                    msg = "❌ Appointment " + json.get("appointmentId").asText()
                            + " has been deleted.";
                    break;
                default:
                    msg = message;
            }

            Notification n = new Notification();
            n.setMessage(msg);
            // tạm coi patientId là userId để thông báo cho bệnh nhân
            n.setUserId(json.has("patientId") ? json.get("patientId").asLong() : 0L);
            n.setRead(false);
            n.setCreatedAt(LocalDateTime.now());

            repo.save(n);
            System.out.println("✅ Notification saved to DB");
        } catch (Exception e) {
            System.err.println("❌ Failed to process message: " + e.getMessage());
        }
    }
}
