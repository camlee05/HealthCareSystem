// package com.hospital.notification.consumer;

// import com.hospital.notification.config.RabbitMQConfig;
// import com.hospital.notification.event.AppointmentEvent;
// import com.hospital.notification.model.Notification;
// import com.hospital.notification.repository.NotificationRepository;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.stereotype.Service;
// import java.time.LocalDateTime;

// @Service
// public class NotificationConsumer {

//     private final NotificationRepository repo;

//     public NotificationConsumer(NotificationRepository repo) {
//         this.repo = repo;
//     }

//     // 📨 Nhận sự kiện từ RabbitMQ
//     @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//     public void receiveAppointmentEvent(AppointmentEvent event) {
//         try {
//             String message;

//             // 🩺 Tạo thông báo theo vai trò (Patient / Doctor / Admin)
//             if (event.getDoctorId() != null) {
//                 message = "👨‍⚕️ Có lịch hẹn mới với bệnh nhân ID: "
//                         + event.getPatientId()
//                         + " vào " + event.getTime();
//             } else {
//                 message = "✅ Đặt lịch thành công vào " + event.getTime();
//             }

//             // 🧩 Chống trùng thông báo
//             if (repo.existsByMessage(message)) {
//                 System.out.println("⚠️ Bỏ qua thông báo trùng: " + message);
//                 return;
//             }

//             Notification noti = new Notification();
//             noti.setUserId(event.getPatientId());  // Có thể mở rộng sau cho doctor/admin
//             noti.setMessage(message);
//             noti.setRead(false);
//             noti.setCreatedAt(LocalDateTime.now());

//             repo.save(noti);
//             System.out.println("📩 Nhận và lưu thông báo: " + message);

//         } catch (Exception e) {
//             System.err.println("❌ Lỗi khi xử lý message RabbitMQ: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }
// }
