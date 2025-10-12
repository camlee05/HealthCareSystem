package com.hospital.appointment_service.repository;

import com.hospital.appointment_service.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    // Tìm tất cả lịch hẹn của 1 bác sĩ
    List<Appointment> findByDoctorId(UUID doctorId);

    // Tìm tất cả lịch hẹn của 1 bệnh nhân
    List<Appointment> findByPatientId(UUID patientId);
}
