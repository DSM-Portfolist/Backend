package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.domain.portfolio.entity.CertificateContainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateContainerRepository extends JpaRepository<CertificateContainer, Long> {

    void deleteByPortfolioId(long portfolioId);
}
