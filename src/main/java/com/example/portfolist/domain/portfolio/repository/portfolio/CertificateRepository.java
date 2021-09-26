package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.portfolio.entity.portfolio.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
