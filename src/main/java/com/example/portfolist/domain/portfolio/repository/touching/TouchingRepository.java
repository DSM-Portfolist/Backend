package com.example.portfolist.domain.portfolio.repository.touching;

import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouchingRepository extends JpaRepository<Touching, TouchingId> {
}
