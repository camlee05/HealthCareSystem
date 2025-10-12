package com.hospital.notification.controller;

import com.hospital.notification.model.Notification;
import com.hospital.notification.repository.NotificationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationRepository repo;

    // Constructor thủ công (không cần Lombok)
    public NotificationController(NotificationRepository repo) {
        this.repo = repo;
    }

    // Tạo thông báo mới
    @PostMapping
    public ResponseEntity<Notification> create(@RequestBody Notification notification) {
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(repo.save(notification));
    }

    // Lấy tất cả thông báo
    @GetMapping
    public List<Notification> getAll() {
        return repo.findAll();
    }

    // Lấy thông báo theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Lấy tất cả thông báo theo userId
    @GetMapping("/user/{userId}")
    public List<Notification> getByUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }

    // Đánh dấu thông báo là đã đọc
    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable Long id) {
        Notification n = repo.findById(id).orElseThrow();
        n.setRead(true);
        return ResponseEntity.ok(repo.save(n));
    }

    // Xóa thông báo theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
