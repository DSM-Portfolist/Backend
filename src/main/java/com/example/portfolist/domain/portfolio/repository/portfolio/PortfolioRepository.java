package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.portfolio.entity.portfolio.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>{
}
