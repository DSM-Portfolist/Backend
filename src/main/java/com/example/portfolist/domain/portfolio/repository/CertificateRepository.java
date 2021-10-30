package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    void deleteByCertificateContainerPortfolioUser(User user);

}
