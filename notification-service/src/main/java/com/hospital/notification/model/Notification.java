package com.hospital.notification.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "uuid", nullable = false)
    private UUID userId;          // người nhận thông báo (UUID)

    @Column(nullable = false, length = 255)
    private String message;

    private boolean read;

    private LocalDateTime createdAt;

    public Notification() {}

    public Notification(Long id, UUID userId, String message, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.read = read;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isRead() { return read; }
    public void setRead(boolean read) { this.read = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
