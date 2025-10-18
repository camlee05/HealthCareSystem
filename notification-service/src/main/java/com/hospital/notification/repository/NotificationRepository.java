package com.hospital.notification.repository;

import com.hospital.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Lấy tất cả thông báo theo userId
    List<Notification> findByUserId(UUID userId);
    boolean existsByMessageAndUserId(String message, UUID userId);

}
