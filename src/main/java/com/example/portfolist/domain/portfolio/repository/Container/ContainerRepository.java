package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.Container;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContainerRepository extends JpaRepository<Container, Long> {

    void deleteByPortfolioUser(User user);
}
