package com.hospital.appointment_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;   // Khóa chính (UUID cho đồng bộ giữa microservices)

    @Column(nullable = false)
    private UUID patientId;   // Tham chiếu sang user-service (Patient)

    @Column(nullable = true)
    private UUID doctorId;    // Tham chiếu sang user-service (Doctor)

    @Column(nullable = false)
    private LocalDateTime appointmentTime;  // Ngày giờ hẹn

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;    // Trạng thái
    public Appointment() {
    }

    public Appointment(UUID id, UUID patientId, UUID doctorId, LocalDateTime appointmentTime, Status status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentTime = appointmentTime;
        this.status = status;
    }

    // Getter & Setter thủ công
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPatientId() {
        return patientId;
    }

    public void setPatientId(UUID patientId) {
        this.patientId = patientId;
    }

    public UUID getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(UUID doctorId) {
        this.doctorId = doctorId;
    }

    public LocalDateTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
