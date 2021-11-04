package com.example.portfolist.domain.portfolio.repository.Container;

import com.example.portfolist.domain.auth.entity.User;
import com.example.portfolist.domain.portfolio.entity.container.ContainerImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContainerImageRepository extends JpaRepository<ContainerImage,Long> {

    void deleteByContainerPortfolioUser(User user);
    void deleteByContainerPortfolioPk(long portfolioId);
}
