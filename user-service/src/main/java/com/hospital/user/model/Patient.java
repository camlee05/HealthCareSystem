package com.hospital.user.model;

import jakarta.persistence.*;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code; // BN001, BN002...

    @Column(nullable = false)
    private String fullName;

    private String gender;
    private String phone;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    // --- Getters & Setters ---
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
