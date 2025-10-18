package com.hospital.notification.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class AppointmentEvent {
    private UUID appointmentId;
    private UUID patientId;
    private UUID doctorId;
    private UUID userId;
    private String status;
    private LocalDateTime time;

    // ✅ constructor đầy đủ
    public AppointmentEvent() {}

    public AppointmentEvent(UUID appointmentId, UUID patientId, UUID userId,
                            UUID doctorId, String status, LocalDateTime time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.userId = userId;
        this.doctorId = doctorId;
        this.status = status;
        this.time = time;
    }

    // ✅ Getters & Setters
    public UUID getAppointmentId() { return appointmentId; }
    public void setAppointmentId(UUID appointmentId) { this.appointmentId = appointmentId; }

    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public UUID getDoctorId() { return doctorId; }
    public void setDoctorId(UUID doctorId) { this.doctorId = doctorId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTime() { return time; }
    public void setTime(LocalDateTime time) { this.time = time; }
}
