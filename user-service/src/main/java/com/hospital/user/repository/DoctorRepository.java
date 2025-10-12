package com.hospital.user.repository;

import com.hospital.user.model.Doctor;
import com.hospital.user.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    List<Doctor> findByDepartment(Department department);
}
