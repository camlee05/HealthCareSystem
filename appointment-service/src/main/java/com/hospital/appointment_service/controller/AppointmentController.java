package com.hospital.appointment_service.controller;

import com.hospital.appointment_service.event.AppointmentEvent;
import com.hospital.appointment_service.model.Appointment;
import com.hospital.appointment_service.model.Status;
import com.hospital.appointment_service.repository.AppointmentRepository;
import com.hospital.appointment_service.service.AppointmentProducer;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*")  // Cho phép frontend gọi API
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository repo;
    private final AppointmentProducer appointmentProducer;

    public AppointmentController(AppointmentRepository repo, AppointmentProducer appointmentProducer) {
        this.repo = repo;
        this.appointmentProducer = appointmentProducer;
    }

    // 🩵 Lấy tất cả lịch hẹn
    @GetMapping
    public List<Appointment> getAll() {
        return repo.findAll();
    }

    // 🩵 Lấy lịch hẹn theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable UUID id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🩵 Lấy lịch hẹn theo bác sĩ
    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getByDoctor(@PathVariable UUID doctorId) {
        return repo.findByDoctorId(doctorId);
    }

    // 🩵 Lấy lịch hẹn theo bệnh nhân
    @GetMapping("/patient/{patientId}")
    public List<Appointment> getByPatient(@PathVariable UUID patientId) {
        return repo.findByPatientId(patientId);
    }

    // 🩵 Tạo mới lịch hẹn + tb
    @PostMapping
    public Appointment create(@RequestBody Appointment appointment) {
        if (appointment.getStatus() == null) {
            appointment.setStatus(Status.REGISTERED);
        }
Appointment saved = repo.save(appointment);

    // 🔄 Gọi sang user-service để lấy thông tin bệnh nhân
    RestTemplate restTemplate = new RestTemplate();
String userServiceUrl = "http://api-gateway:8080/user-service/api/patients/" + saved.getPatientId();

    try {
        ResponseEntity<Map> response = restTemplate.getForEntity(userServiceUrl, Map.class);
        Map patientData = response.getBody();

        // ✅ Lấy userId từ JSON trả về
        String userIdStr = (String) patientData.get("userId");

        appointmentProducer.sendAppointmentEvent(
            new AppointmentEvent(
                saved.getId(),
                saved.getPatientId(),
                UUID.fromString(userIdStr),   // ✅ Gửi userId thật
                saved.getDoctorId(),
                saved.getStatus().name(),
                saved.getAppointmentTime()
            )
        );

        System.out.println("📤 Event gửi thành công với userId: " + userIdStr);

    } catch (Exception e) {
        System.err.println("❌ Không lấy được userId từ user-service: " + e.getMessage());
        // fallback: vẫn gửi event, nhưng dùng patientId tạm
        appointmentProducer.sendAppointmentEvent(
            new AppointmentEvent(
                saved.getId(),
                saved.getPatientId(),
                saved.getPatientId(),
                saved.getDoctorId(),
                saved.getStatus().name(),
                saved.getAppointmentTime()
            )
        );
    }

    return saved;
}

    // 🩵 Cập nhật trạng thái hoặc gán bác sĩ
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestBody Map<String, Object> body) {
        String statusStr = (String) body.get("status");

        if (statusStr == null || statusStr.isBlank()) {
            return ResponseEntity.badRequest().body("Thiếu giá trị status trong request body");
        }

        try {
            Status newStatus = Status.valueOf(statusStr.toUpperCase());

            return repo.findById(id)
                    .map(appointment -> {

                        // ✅ Nếu có doctorId trong request thì xử lý
                        if (body.containsKey("doctorId")) {
                            Object doctorIdObj = body.get("doctorId");

                            if (doctorIdObj == null) {
                                // ↩️ Hủy lịch → bỏ bác sĩ
                                appointment.setDoctorId(null);
                            } else {
                                try {
                                    appointment.setDoctorId(UUID.fromString(doctorIdObj.toString()));
                                } catch (Exception e) {
                                    return ResponseEntity.badRequest().body("Giá trị doctorId không hợp lệ");
                                }
                            }
                        }

                        appointment.setStatus(newStatus);
                        repo.save(appointment);
                        return ResponseEntity.ok(appointment);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Giá trị status không hợp lệ: " + statusStr);
        }
    }

    // 🩵 Xóa lịch hẹn
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return repo.findById(id)
                .map(appointment -> {
                    repo.delete(appointment);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    

    // @GetMapping("/test-message")
    // public String sendTestMessage() {
    //     appointmentProducer.sendTestMessage("Xin chào từ AppointmentService!");
    //     return "✅ Đã gửi message đến RabbitMQ!";
    // }
    
}
