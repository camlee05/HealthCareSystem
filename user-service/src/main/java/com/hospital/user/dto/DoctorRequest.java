package com.hospital.user.dto;

import java.util.UUID;

/**
 * DTO dùng để nhận dữ liệu từ frontend khi admin thêm bác sĩ mới.
 * Không ánh xạ trực tiếp vào entity -> giúp bảo mật và tách biệt tầng dữ liệu.
 */
public class DoctorRequest {
    private String fullName;
    private String phone;
    private UUID departmentId; // ID của khoa bác sĩ làm việc
    private String username;   // Tài khoản đăng nhập bác sĩ
    private String password;   // Mật khẩu (sẽ được mã hóa trước khi lưu)

    // --- Getters & Setters ---
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(UUID departmentId) {
        this.departmentId = departmentId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
