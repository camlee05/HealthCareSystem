package com.hospital.diagnosis.controller;

import com.hospital.diagnosis.model.Diagnosis;
import com.hospital.diagnosis.repository.DiagnosisRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = "*") // ✅ Cho phép frontend gọi API
@RestController
@RequestMapping("/api/diagnoses")
public class DiagnosisController {

    private final DiagnosisRepository repo;

    public DiagnosisController(DiagnosisRepository repo) {
        this.repo = repo;
    }

    // 🩺 Tạo diagnosis mới
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Diagnosis diagnosis) {
        if (diagnosis.getPatientId() == null || diagnosis.getDoctorId() == null || diagnosis.getAppointmentId() == null) {
            return ResponseEntity.badRequest().body("Thiếu thông tin bệnh nhân, bác sĩ hoặc lịch hẹn!");
        }

        diagnosis.setDate(LocalDate.now());

        Diagnosis saved = repo.save(diagnosis);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 🩺 Lấy tất cả diagnosis
    @GetMapping
    public List<Diagnosis> getAll() {
        return repo.findAll();
    }

    // 🩺 Lấy diagnosis theo id
    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🩺 Lấy diagnosis theo appointmentId
    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<Diagnosis> getByAppointment(@PathVariable UUID appointmentId) {
        return repo.findByAppointmentId(appointmentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🩺 Lấy tất cả diagnosis theo patientId
    @GetMapping("/patient/{patientId}")
    public List<Diagnosis> getByPatient(@PathVariable UUID patientId) {
        return repo.findByPatientId(patientId);
    }

    // 🩺 Lấy tất cả diagnosis theo doctorId
    @GetMapping("/doctor/{doctorId}")
    public List<Diagnosis> getByDoctor(@PathVariable UUID doctorId) {
        return repo.findByDoctorId(doctorId);
    }

    // 🗑️ Xóa diagnosis theo id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ================================================================
    // 🩻 THÊM MỚI: Upload và Xem ảnh phim chuẩn đoán
    // ================================================================

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<?> uploadDiagnosisImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        try {
            Diagnosis diag = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh án!"));

            // 🗂️ Thư mục lưu ảnh trong container
            String uploadDir = "/app/uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            // 🔹 Đặt tên file duy nhất
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);

            // 🔹 Lưu file
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // 🔹 Ghi thông tin vào DB
            diag.setImagePath(filePath.toString());
            diag.setImageName(file.getOriginalFilename());
            diag.setImageType(file.getContentType());
            repo.save(diag);

            return ResponseEntity.ok(Map.of(
                    "message", "✅ Upload ảnh thành công!",
                    "fileName", diag.getImageName(),
                    "url", "/api/diagnoses/" + id + "/image"
            ));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Lỗi khi lưu file ảnh!");
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<Resource> viewDiagnosisImage(@PathVariable Long id) {
        try {
            Diagnosis diag = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy bệnh án!"));

            if (diag.getImagePath() == null)
                return ResponseEntity.notFound().build();

            Path path = Paths.get(diag.getImagePath());
            Resource resource = new UrlResource(path.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(diag.getImageType()))
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).build();
        }
    }
}
