package com.example.portfolist.domain.auth.repository.repository;

import com.example.portfolist.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNormalUserEmail(String email);
    boolean existsByNormalUserEmail(String email);
}
