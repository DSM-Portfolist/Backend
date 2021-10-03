package com.example.portfolist.domain.portfolio.repository.portfolio;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.portfolio.PortfolioField;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioFieldRepository extends JpaRepository<PortfolioField, Long> {

    void deleteByPortfolioUser(User user);

}
