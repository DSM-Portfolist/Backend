package com.example.portfolist.domain.auth.repository.repository;

import com.example.portfolist.domain.auth.entity.Field;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldRepository extends JpaRepository<Field, Long> {
}
