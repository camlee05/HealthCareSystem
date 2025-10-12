package com.hospital.user.controller;

import com.hospital.user.model.Patient;
import com.hospital.user.model.User;
import com.hospital.user.repository.PatientRepository;
import com.hospital.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "*")
public class PatientController {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientController(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }

    // --- Lấy tất cả bệnh nhân ---
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Patient> patients = patientRepository.findAll();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Patient p : patients) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("code", p.getCode());
            map.put("fullName", p.getFullName());
            map.put("gender", p.getGender());
            map.put("phone", p.getPhone());
            map.put("address", p.getAddress());
            map.put("userId", p.getUser() != null ? p.getUser().getId() : null);
            response.add(map);
        }
        return ResponseEntity.ok(response);
    }

    // --- Lấy bệnh nhân theo ID ---
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", patient.getId());
                    map.put("code", patient.getCode());
                    map.put("fullName", patient.getFullName());
                    map.put("gender", patient.getGender());
                    map.put("phone", patient.getPhone());
                    map.put("address", patient.getAddress());
                    map.put("userId", patient.getUser() != null ? patient.getUser().getId() : null);
                    return ResponseEntity.ok(map);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Tạo mới hoặc dùng lại hồ sơ cũ ---
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Patient patient) {
        try {
            if (patient.getUser() == null || patient.getUser().getId() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Thiếu thông tin user.id trong request"));
            }

            UUID userId = patient.getUser().getId();
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + userId));

            // Kiểm tra user đã có hồ sơ bệnh nhân chưa
            Optional<Patient> existing = patientRepository.findAll().stream()
                    .filter(p -> p.getUser().getId().equals(userId))
                    .findFirst();

            if (existing.isPresent()) {
                Patient existingPatient = existing.get();
                Map<String, Object> res = new HashMap<>();
                res.put("id", existingPatient.getId());
                res.put("code", existingPatient.getCode());
                res.put("fullName", existingPatient.getFullName());
                res.put("gender", existingPatient.getGender());
                res.put("phone", existingPatient.getPhone());
                res.put("address", existingPatient.getAddress());
                res.put("userId", existingPatient.getUser().getId());
                res.put("message", "Đã tồn tại, dùng lại hồ sơ cũ");
                return ResponseEntity.ok(res);
            }

            // Sinh mã bệnh nhân BN001, BN002, ...
            long count = patientRepository.count() + 1;
            String newCode = String.format("BN%03d", count);
            patient.setCode(newCode);
            patient.setUser(user);

            Patient saved = patientRepository.save(patient);

            Map<String, Object> res = new HashMap<>();
            res.put("id", saved.getId());
            res.put("code", saved.getCode());
            res.put("fullName", saved.getFullName());
            res.put("gender", saved.getGender());
            res.put("phone", saved.getPhone());
            res.put("address", saved.getAddress());
            res.put("userId", saved.getUser().getId());
            res.put("message", "Tạo hồ sơ mới thành công");

            return ResponseEntity.ok(res);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Lỗi khi lưu bệnh nhân");
            err.put("details", e.getMessage());
            return ResponseEntity.internalServerError().body(err);
        }
    }

    // --- Cập nhật ---
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Patient updated) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patient.setFullName(updated.getFullName());
                    patient.setGender(updated.getGender());
                    patient.setPhone(updated.getPhone());
                    patient.setAddress(updated.getAddress());
                    Patient saved = patientRepository.save(patient);

                    Map<String, Object> map = new HashMap<>();
                    map.put("id", saved.getId());
                    map.put("code", saved.getCode());
                    map.put("fullName", saved.getFullName());
                    map.put("gender", saved.getGender());
                    map.put("phone", saved.getPhone());
                    map.put("address", saved.getAddress());

                    return ResponseEntity.ok(map);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- Xóa ---
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return patientRepository.findById(id)
                .map(patient -> {
                    patientRepository.delete(patient);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
