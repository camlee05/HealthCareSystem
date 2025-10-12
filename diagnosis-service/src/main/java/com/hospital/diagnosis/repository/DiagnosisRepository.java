package com.hospital.diagnosis.repository;

import com.hospital.diagnosis.model.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    List<Diagnosis> findByPatientId(UUID patientId);

    List<Diagnosis> findByDoctorId(UUID doctorId);

    Optional<Diagnosis> findByAppointmentId(UUID appointmentId);
}
