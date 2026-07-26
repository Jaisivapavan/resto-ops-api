package com.sivapavan.restoops.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sivapavan.restoops.entity.User;

@Repository  // ← Add this (good practice)
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ✅ CORRECT! Let Spring Data JPA implement this
    Optional<User> findByEmail(String email);
    
    // ✅ CORRECT! Let Spring Data JPA implement this
    boolean existsByEmail(String email);
}