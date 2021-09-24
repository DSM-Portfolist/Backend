package com.example.portfolist.domain.auth.repository.repository;

import com.example.portfolist.domain.auth.entity.NormalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalUserRepository extends JpaRepository<NormalUser, Long> {
}
