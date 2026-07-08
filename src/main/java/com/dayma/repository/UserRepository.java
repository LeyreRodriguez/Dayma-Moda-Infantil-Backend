package com.dayma.repository;

import com.dayma.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByVerificationCode(String verificationCode);
    List<User> findByNewsletterTrue();

    long countByRegistrationDateBetween(LocalDateTime start, LocalDateTime end);
}
