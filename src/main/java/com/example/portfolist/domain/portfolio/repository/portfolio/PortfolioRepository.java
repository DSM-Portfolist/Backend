package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, QuerydslRepository {

    void deleteByUser(User user);

    Page<Portfolio> findAllByOrderByDateDesc(Pageable pageable);
}
