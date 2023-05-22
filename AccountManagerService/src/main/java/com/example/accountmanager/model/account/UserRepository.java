package com.example.accountmanager.model.account;

import com.example.accountmanager.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "Select a From users a WHERE a.email = ?1")
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<User> findByRoles(Role role);
}
