package com.example.portfolist.domain.portfolio.repository;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.Portfolio;
import com.example.portfolist.domain.portfolio.entity.touching.Touching;
import com.example.portfolist.domain.portfolio.entity.touching.TouchingId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TouchingRepository extends JpaRepository<Touching, TouchingId> {

    Page<Touching> findByUser(Pageable pageable, User user);
    void deleteByPortfolioUser(User user);
    void deleteByUser(User user);

    List<Touching> findByPortfolio(Portfolio portfolio);
}
