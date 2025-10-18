package com.hospital.notification.service;

import com.hospital.notification.event.AppointmentEvent;
import com.hospital.notification.model.Notification;
import com.hospital.notification.repository.NotificationRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AppointmentListener {

    private final NotificationRepository repo;

    public AppointmentListener(NotificationRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = "appointment.notifications")
    public void receive(AppointmentEvent event) {
        System.out.println("💌 Nhận event từ RabbitMQ:");
        System.out.println("📦 Appointment ID: " + event.getAppointmentId());
        System.out.println("🧍 Patient ID: " + event.getUserId());
        System.out.println("👨‍⚕️ Doctor ID: " + event.getDoctorId());
        System.out.println("🕒 Time: " + event.getTime());

        String timeStr = (event.getTime() != null) ? event.getTime().toString() : "chưa xác định";

        // 🧍 BỆNH NHÂN — Đặt lịch thành công
        String patientMsg = "✅ Đặt lịch thành công vào " + timeStr;
        if (!repo.existsByMessageAndUserId(patientMsg, event.getUserId())) {
            Notification notiForPatient = new Notification();
            notiForPatient.setUserId(event.getUserId());
            notiForPatient.setMessage(patientMsg);
            notiForPatient.setRead(false);
            notiForPatient.setCreatedAt(LocalDateTime.now());
            repo.save(notiForPatient);
        }

        // 🧑‍⚕️ BÁC SĨ — Có lịch hẹn mới với bệnh nhân
        if (event.getDoctorId() != null) {
            String doctorMsg = "📥 Có lịch hẹn mới với bệnh nhân ID: "
                    + event.getUserId() + " vào " + timeStr;

            if (!repo.existsByMessageAndUserId(doctorMsg, event.getDoctorId())) {
                Notification notiForDoctor = new Notification();
                notiForDoctor.setUserId(event.getDoctorId());
                notiForDoctor.setMessage(doctorMsg);
                notiForDoctor.setRead(false);
                notiForDoctor.setCreatedAt(LocalDateTime.now());
                repo.save(notiForDoctor);
            }
        } else {
            System.out.println("⚠️ Không có doctorId — bỏ qua thông báo cho bác sĩ.");
        }

        // 🧑‍💼 ADMIN — Nhận thông báo tổng (qua user-service)
        try {
            RestTemplate rest = new RestTemplate();
            String adminUrl = "http://user-service:8081/api/users/role/ADMIN"; // 🔧 gọi thẳng sang service qua Docker network
            ResponseEntity<List> response = rest.getForEntity(adminUrl, List.class);
            List<Map<String, Object>> admins = response.getBody();

            if (admins != null && !admins.isEmpty()) {
                for (Map<String, Object> admin : admins) {
                    String adminIdStr = (String) admin.get("id");
                    UUID adminId = UUID.fromString(adminIdStr);
                    String adminMsg = "⚙️ Có lịch hẹn mới từ bệnh nhân ID: "
                            + event.getUserId() + " vào " + timeStr;

                    if (!repo.existsByMessageAndUserId(adminMsg, adminId)) {
                        Notification notiForAdmin = new Notification();
                        notiForAdmin.setUserId(adminId);
                        notiForAdmin.setMessage(adminMsg);
                        notiForAdmin.setRead(false);
                        notiForAdmin.setCreatedAt(LocalDateTime.now());
                        repo.save(notiForAdmin);
                    }
                }
            } else {
                System.err.println("⚠️ Không tìm thấy admin nào trong user-service");
            }
        } catch (Exception e) {
            System.err.println("❌ Không lấy được admin từ user-service: " + e.getMessage());
        }

        System.out.println("💾 Đã lưu thông báo cho Patient + Doctor + Admin");
    }
}
