package com.hospital.user.controller;

import com.hospital.user.model.User;
import com.hospital.user.model.Role;
import com.hospital.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- Đăng ký ---
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        String roleStr = payload.getOrDefault("role", "PATIENT").toUpperCase();

        // Kiểm tra trùng username
        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Tên đăng nhập đã tồn tại!"));
        }

        // Xác định role hợp lệ
        Role role;
        try {
            role = Role.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Vai trò không hợp lệ!"));
        }

        // Tạo user mới
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);

        User savedUser = userRepository.save(newUser);
        
        // 🧾 In log kiểm tra
        System.out.println("=== DEBUG SIGNUP ===");
        System.out.println("Saved User: " + savedUser);
        System.out.println("User ID: " + savedUser.getId());

        // Trả về thông tin user để frontend lưu
        return ResponseEntity.ok(Map.of(
                "message", "Đăng ký thành công!",
                "id", newUser.getId(),
                "username", newUser.getUsername(),
                "role", newUser.getRole().toString()
        ));
    }

    // --- Đăng nhập ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> payload) {
        String username = payload.get("username");
        String password = payload.get("password");
        String roleStr = payload.getOrDefault("role", "PATIENT").toUpperCase();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Không tìm thấy người dùng!"));
        }

        User user = userOpt.get();

        // ✅ Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(401).body(Map.of("message", "Sai mật khẩu!"));
        }

        // ✅ Kiểm tra role có khớp không
        if (!user.getRole().toString().equals(roleStr)) {
            return ResponseEntity.status(403).body(Map.of(
                "message", "Vai trò đăng nhập không khớp với tài khoản!"
            ));
        }

        // ✅ Nếu mọi thứ OK → trả về thông tin user
        return ResponseEntity.ok(Map.of(
            "message", "Đăng nhập thành công!",
            "id", user.getId(),
            "username", user.getUsername(),
            "role", user.getRole().toString()
        ));
    }
}