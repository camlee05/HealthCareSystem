package com.hospital.user.repository;

import com.hospital.user.model.User;
import com.hospital.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);

}
