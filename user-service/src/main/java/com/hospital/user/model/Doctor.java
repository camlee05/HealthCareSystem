package com.hospital.user.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue
    private UUID id;   // 🔹 Tự sinh UUID

    @Column(nullable = false)
    private String fullName;  // 🔹 Tên bác sĩ

    private String phone;     // 🔹 Số điện thoại

    // 🔹 Nhiều bác sĩ thuộc 1 khoa
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    // 🔹 Mỗi bác sĩ liên kết với 1 tài khoản User
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // --- Constructors ---
    public Doctor() {}

    public Doctor(String fullName, String phone, Department department, User user) {
        this.fullName = fullName;
        this.phone = phone;
        this.department = department;
        this.user = user;
    }

    // --- Getters & Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
