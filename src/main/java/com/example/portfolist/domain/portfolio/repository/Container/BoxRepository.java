package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.portfolio.entity.container.Box;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxRepository extends JpaRepository<Box,Long> {
}
