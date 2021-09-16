package com.example.portfolist.domain.auth.repository;

import com.example.portfolist.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
