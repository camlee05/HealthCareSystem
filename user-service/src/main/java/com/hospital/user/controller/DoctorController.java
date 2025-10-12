package com.hospital.user.controller;

import com.hospital.user.dto.DoctorRequest;
import com.hospital.user.model.*;
import com.hospital.user.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "*")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DoctorController(DoctorRepository doctorRepository,
                            DepartmentRepository departmentRepository,
                            UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    // ===============================
    // 🧩 1️⃣ Thêm mới bác sĩ (Admin)
    // ===============================
    @PostMapping
    public ResponseEntity<?> createDoctor(@RequestBody DoctorRequest req) {
        try {
            // --- Kiểm tra username trùng ---
            if (userRepository.findByUsername(req.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "❌ Username đã tồn tại!"));
            }

            // --- Tìm Department ---
            Department department = departmentRepository.findById(req.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy khoa với ID: " + req.getDepartmentId()));

            // --- Tạo User (tài khoản đăng nhập) ---
            User user = new User();
            user.setUsername(req.getUsername());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setRole(Role.DOCTOR);
            userRepository.save(user);

            // --- Tạo Doctor ---
            Doctor doctor = new Doctor();
            doctor.setFullName(req.getFullName());
            doctor.setPhone(req.getPhone());
            doctor.setDepartment(department);
            doctor.setUser(user);
            doctorRepository.save(doctor);

            return ResponseEntity.ok(Map.of(
                    "message", "✅ Tạo bác sĩ thành công!",
                    "doctorId", doctor.getId(),
                    "username", user.getUsername()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of(
                    "error", "Lỗi khi tạo bác sĩ",
                    "details", e.getMessage()
            ));
        }
    }

    // ===============================
    // 🧩 2️⃣ Lấy tất cả bác sĩ
    // ===============================
    @GetMapping
    public ResponseEntity<?> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Doctor d : doctors) {
            response.add(Map.of(
                    "id", d.getId(),
                    "fullName", d.getFullName(),
                    "phone", d.getPhone(),
                    "department", d.getDepartment() != null ? d.getDepartment().getName() : null,
                    "user", d.getUser() != null ? d.getUser().getUsername() : null
            ));
        }

        return ResponseEntity.ok(response);
    }

    // ===============================
    // 🧩 3️⃣ Lấy bác sĩ theo ID
    // ===============================
    @GetMapping("/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable UUID id) {
        return doctorRepository.findById(id)
                .map(d -> ResponseEntity.ok(Map.of(
                        "id", d.getId(),
                        "fullName", d.getFullName(),
                        "phone", d.getPhone(),
                        "department", d.getDepartment() != null ? d.getDepartment().getName() : null,
                        "user", d.getUser() != null ? d.getUser().getUsername() : null
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    // ===============================
    // 🧩 4️⃣ Xóa bác sĩ
    // ===============================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable UUID id) {
        return doctorRepository.findById(id)
                .map(doctor -> {
                    // Xóa cả user đi kèm
                    User user = doctor.getUser();
                    doctorRepository.delete(doctor);
                    if (user != null) {
                        userRepository.delete(user);
                    }
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
