package com.hospital.user.controller;

import com.hospital.user.model.Department;
import com.hospital.user.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")   // ✅ đổi thành /api/users thay vì /api/departments
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // Lấy tất cả departments
    @GetMapping("/departments")   // ✅ thêm /departments phía sau
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    // Lấy department theo id
    @GetMapping("/departments/{id}")
    public ResponseEntity<Department> getById(@PathVariable UUID id) {
        return departmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Tạo mới department
    @PostMapping("/departments")
    public Department create(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    // Cập nhật department
    @PutMapping("/departments/{id}")
    public ResponseEntity<Department> update(@PathVariable UUID id,
                                             @RequestBody Department updated) {
        return departmentRepository.findById(id)
                .map(department -> {
                    department.setName(updated.getName());
                    department.setDescription(updated.getDescription());
                    return ResponseEntity.ok(departmentRepository.save(department));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Xóa department
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return departmentRepository.findById(id)
                .map(department -> {
                    departmentRepository.delete(department);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
