package com.hospital.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;   // Ví dụ: Nội, Ngoại, Tim mạch

    private String description; // Mô tả về khoa

    @JsonIgnore  // 🚫 Ngăn không serialize danh sách bác sĩ để tránh vòng lặp vô hạn
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors;  // Một khoa có nhiều bác sĩ

    // --- Constructors ---
    public Department() {}

    public Department(UUID id, String name, String description, List<Doctor> doctors) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.doctors = doctors;
    }

    // --- Getters & Setters ---
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
