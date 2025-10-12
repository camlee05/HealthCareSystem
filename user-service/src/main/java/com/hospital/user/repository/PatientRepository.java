package com.hospital.user.repository;

import com.hospital.user.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Patient findByPhone(String phone);
}
